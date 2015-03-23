package io.sphere.sdk.client;

import io.sphere.sdk.client.AuthActorProtocol.*;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.utils.CompletableFutureUtils;


import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 *  Holds OAuth access tokenCache for accessing protected Sphere HTTP API endpoints.
 *  Refreshes the access token as needed automatically.
 */
final class AutoRefreshSphereAccessTokenSupplierImpl extends AutoCloseableService implements SphereAccessTokenSupplier {
    private volatile CompletableFuture<String> currentAccessTokenFuture = new CompletableFuture<>();
    private volatile Optional<Tokens> currentTokensOption = Optional.empty();
    private final Actor tokenActor = new TokenActor();
    private final Actor authActor;

    private AutoRefreshSphereAccessTokenSupplierImpl(final SphereAuthConfig config, final HttpClient httpClient, final boolean closeHttpClient) {
        final TokensSupplier internalTokensSupplier = TokensSupplierImpl.of(config, httpClient, closeHttpClient);
        authActor = new AuthActor(internalTokensSupplier);
        authActor.tell(new SubscribeMessage(tokenActor));
    }

    @Override
    public CompletionStage<String> get() {
        return currentAccessTokenFuture;
    }

    @Override
    protected void internalClose() {
        closeQuietly(authActor);
        closeQuietly(tokenActor);
    }

    public static SphereAccessTokenSupplier createAndBeginRefreshInBackground(final SphereAuthConfig config, final HttpClient httpClient, final boolean closeHttpClient) {
        return new AutoRefreshSphereAccessTokenSupplierImpl(config, httpClient, closeHttpClient);
    }

    private class TokenActor extends Actor {
        @Override
        protected void receive(final Object message) {
            receiveBuilder(message)
                    .when(TokenDeliveredMessage.class, m -> {
                        if (!currentTokensOption.isPresent() || currentTokenIsOlder(m.tokens)) {
                            updateToken(m.tokens);
                        }
                    })
                    .when(TokenDeliveryFailedMessage.class, m -> {
                        if (!currentTokensOption.isPresent()) {
                            currentAccessTokenFuture.completeExceptionally(m.cause);
                        } else if(lastTokenIsStillValid()){
                            //keep the old token
                        } else {
                            currentTokensOption = Optional.empty();
                            currentAccessTokenFuture = CompletableFutureUtils.failed(m.cause);
                        }
                    });
        }
    }

    /**
     * if the last token has no expire time it true
     * @return
     */
    private boolean lastTokenIsStillValid() {
        if (currentTokensOption.isPresent()) {
            final Tokens oldTokens = currentTokensOption.get();
            return oldTokens.getExpiresInstant().map(expireTime -> expireTime.isAfter(Instant.now())).orElse(true);
        } else {
            return false;
        }
    }

    private boolean currentTokenIsOlder(final Tokens newTokens) {
        return (currentTokensOption.isPresent() && oldExpiringInstant().isBefore(newExpiringInstant(newTokens)));
    }

    private Instant newExpiringInstant(final Tokens newTokens) {
        return newTokens.getExpiresInstant().orElseGet(() -> Instant.now().plusSeconds(30 * 60));
    }

    private Instant oldExpiringInstant() {
        return currentTokensOption.get().getExpiresInstant().orElseGet(() -> Instant.now());
    }

    private void updateToken(final Tokens tokens) {
        currentTokensOption = Optional.of(tokens);
        final String accessToken = tokens.getAccessToken();
        if (currentAccessTokenFuture.isDone()) {
            currentAccessTokenFuture = CompletableFutureUtils.successful(accessToken);
        } else {
            currentAccessTokenFuture.complete(accessToken);
        }
    }
}
