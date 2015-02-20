package io.sphere.sdk.client;

import io.sphere.sdk.client.AuthActorProtocol.*;
import io.sphere.sdk.http.HttpClient;


import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 *  Holds OAuth access tokenCache for accessing protected Sphere HTTP API endpoints.
 *  Refreshes the access token as needed automatically.
 */
final class AutoRefreshSphereAccessTokenSupplierImpl extends AutoCloseableService implements SphereAccessTokenSupplier {
    private volatile CompletableFuture<String> lastToken = new CompletableFuture<>();
    private volatile Optional<Tokens> tokensOption = Optional.empty();
    private final Actor tokenActor = new TokenActor();
    private final Actor authActor;

    private AutoRefreshSphereAccessTokenSupplierImpl(final SphereAuthConfig config, final HttpClient httpClient, final boolean closeHttpClient) {
        final TokensSupplier internalTokensSupplier = TokensSupplierImpl.of(config, httpClient, closeHttpClient);
        authActor = new AuthActor(internalTokensSupplier);
        authActor.tell(new SubscribeMessage(tokenActor));
    }

    @Override
    public CompletableFuture<String> get() {
        return lastToken;
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
                        if (!tokensOption.isPresent() || currentTokenIsOlder(m.tokens)) {
                            updateToken(m.tokens);
                        }
                    })
                    .when(TokenDeliveryFailedMessage.class, m -> {
                        if (!tokensOption.isPresent()) {
                            lastToken.completeExceptionally(m.cause);
                        } else if(lastTokenIsStillValid()){
                            //keep the old token
                        } else {
                            tokensOption = Optional.empty();
                            lastToken = CompletableFutureUtils.failed(m.cause);
                        }
                    });
        }
    }

    /**
     * if the last token has no expire time it true
     * @return
     */
    private boolean lastTokenIsStillValid() {
        if (tokensOption.isPresent()) {
            final Tokens oldTokens = tokensOption.get();
            return oldTokens.getExpiresInstant().map(expireTime -> expireTime.isAfter(Instant.now())).orElse(true);
        } else {
            return false;
        }
    }

    private boolean currentTokenIsOlder(final Tokens newTokens) {
        return (tokensOption.isPresent() && oldExpiringInstant().isBefore(newExpiringInstant(newTokens)));
    }

    private Instant newExpiringInstant(final Tokens newTokens) {
        return newTokens.getExpiresInstant().orElseGet(() -> Instant.now().plusSeconds(30 * 60));
    }

    private Instant oldExpiringInstant() {
        return tokensOption.get().getExpiresInstant().orElseGet(() -> Instant.now());
    }

    private void updateToken(final Tokens tokens) {
        tokensOption = Optional.of(tokens);
        if (lastToken.isDone()) {
            lastToken = CompletableFutureUtils.successful(tokens.getAccessToken());
        } else {
            lastToken.complete(tokens.getAccessToken());
        }
    }
}
