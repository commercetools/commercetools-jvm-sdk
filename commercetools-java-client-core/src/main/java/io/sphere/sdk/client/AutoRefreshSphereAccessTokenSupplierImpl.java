package io.sphere.sdk.client;

import io.sphere.sdk.client.AuthActorProtocol.*;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.utils.CompletableFutureUtils;


import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static io.sphere.sdk.client.SphereAuth.AUTH_LOGGER;

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
        rejectExcutionIfClosed("Token supplier is already closed.");
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
                        final boolean hasInvalidCredentials = m.cause.getCause() != null && m.cause.getCause() instanceof InvalidClientCredentialsException;
                        if (hasInvalidCredentials) {
                            AUTH_LOGGER.error(() -> "Invalid client credentials shutting down.", m.cause);
                            currentAccessTokenFuture.completeExceptionally(m.cause);//in case it is still empty
                            currentAccessTokenFuture = CompletableFutureUtils.failed(m.cause);//in case it was not empty
                            close();
                        } else if (!currentTokensOption.isPresent()) {
                            currentAccessTokenFuture.completeExceptionally(m.cause);
                        } else if (lastTokenIsStillValid()) {
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
            return Optional.ofNullable(oldTokens.getExpiresZonedDateTime()).map(expireTime -> expireTime.isAfter(ZonedDateTime.now())).orElse(true);
        } else {
            return false;
        }
    }

    private boolean currentTokenIsOlder(final Tokens newTokens) {
        return (currentTokensOption.isPresent() && oldExpiringZonedDateTime().isBefore(newExpiringZonedDateTime(newTokens)));
    }

    private ZonedDateTime newExpiringZonedDateTime(final Tokens newTokens) {
        return Optional.ofNullable(newTokens.getExpiresZonedDateTime()).orElseGet(() -> ZonedDateTime.now().plusSeconds(30 * 60));
    }

    private ZonedDateTime oldExpiringZonedDateTime() {
        return Optional.ofNullable(currentTokensOption.get().getExpiresZonedDateTime()).orElseGet(() -> ZonedDateTime.now());
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
