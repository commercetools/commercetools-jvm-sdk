package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;

import java.util.concurrent.CompletionStage;

final class OnDemandSphereAccessTokenSupplier extends AutoCloseableService implements SphereAccessTokenSupplier {
    private final TokensSupplier tokensSupplier;

    private OnDemandSphereAccessTokenSupplier(final SphereAuthConfig config, final HttpClient httpClient, final boolean closeHttpClient) {
        tokensSupplier = TokensSupplierImpl.of(config, httpClient, closeHttpClient);
    }

    @Override
    protected synchronized void internalClose() {
        if (!isClosed()) {
            tokensSupplier.close();
        }
    }

    @Override
    public CompletionStage<String> get() {
        rejectExcutionIfClosed("Token supplier is already closed.");
        return tokensSupplier.get().thenApply(Tokens::getAccessToken);
    }

    public static OnDemandSphereAccessTokenSupplier of(final SphereAuthConfig config, final HttpClient httpClient, final boolean closeHttpClient) {
        return new OnDemandSphereAccessTokenSupplier(config, httpClient, closeHttpClient);
    }
}
