package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;

import java.util.concurrent.CompletableFuture;

import static io.sphere.sdk.client.SphereAuth.*;

final class OnDemandSphereAccessTokenSupplier extends AutoCloseableService implements SphereAccessTokenSupplier {
    private final TokensSupplier tokensSupplier;
    private boolean isClosed = false;

    private OnDemandSphereAccessTokenSupplier(final SphereAuthConfig config, final HttpClient httpClient, final boolean closeHttpClient) {
        tokensSupplier = TokensSupplierImpl.of(config, httpClient, closeHttpClient);
    }

    @Override
    protected synchronized void internalClose() {
        if (!isClosed) {
            tokensSupplier.close();
            isClosed = true;
        }
    }

    @Override
    public CompletableFuture<String> get() {
        return tokensSupplier.get().thenApply(Tokens::getAccessToken);
    }

    public static OnDemandSphereAccessTokenSupplier of(final SphereAuthConfig config, final HttpClient httpClient, final boolean closeHttpClient) {
        return new OnDemandSphereAccessTokenSupplier(config, httpClient, closeHttpClient);
    }
}
