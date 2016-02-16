package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

import java.util.concurrent.CompletionStage;

/**
 * Provides facilities to fetch commercetools access and refresh tokens.
 */
public final class TokensFacade extends Base {
    private TokensFacade() {
    }

    public static CompletionStage<String> fetchAccessToken(final SphereAuthConfig authConfig) {
        return fetchTokens(authConfig).thenApply(Tokens::getAccessToken);
    }

    /**
     * Fetches a new access token.
     *
     * {@include.example io.sphere.sdk.client.TokensFacadeIntegrationTest#fetchAccessToken()}
     *
     * @param authConfig the commercetools project which the token should belong to
     * @return token
     */
    public static CompletionStage<Tokens> fetchTokens(final SphereAuthConfig authConfig) {
        return TokensSupplier.of(authConfig, SphereClientFactory.of().createHttpClient(), true).get();
    }
}
