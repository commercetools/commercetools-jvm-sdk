package io.sphere.sdk.client;

import java.util.concurrent.CompletableFuture;

final class SphereConstantAccessTokenSupplierImpl extends AutoCloseableService implements SphereAccessTokenSupplier {
    private final CompletableFuture<String> token;

    SphereConstantAccessTokenSupplierImpl(final String token) {
        this.token = CompletableFutureUtils.successful(token);
    }

    @Override
    public CompletableFuture<String> get() {
        return token;
    }

    @Override
    protected void internalClose() {
    }
}
