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
     * Fetches a new access token using the client credentials flow.
     *
     * {@include.example io.sphere.sdk.client.TokensFacadeIntegrationTest#fetchAccessToken()}
     *
     * @param authConfig the commercetools project which the token should belong to
     * @return token
     */
    public static CompletionStage<Tokens> fetchTokens(final SphereAuthConfig authConfig) {
        return TokensSupplier.of(authConfig, SphereClientFactory.of().createHttpClient(), true).get();
    }

    /**
     * Fetches a new access token using the customer password flow.
     *
     * {@include.example io.sphere.sdk.client.TokensFacadeIntegrationTest#passwordFlowDemo()}
     *
     * @param authConfig authConfig
     * @param email email
     * @param password password
     * @return token
     */
    public static CompletionStage<Tokens> fetchCustomerPasswordFlowTokens(final SphereAuthConfig authConfig, final String email, final String password) {
        return TokensSupplier.ofCustomerPasswordFlowTokens(authConfig, email, password, SphereClientFactory.of().createHttpClient(), true).get();
    }
}
