package io.sphere.sdk.client;

import java.util.concurrent.CompletionStage;

final class SphereConstantAccessTokenSupplierImpl extends AutoCloseableService implements SphereAccessTokenSupplier {
    private final CompletionStage<String> token;

    SphereConstantAccessTokenSupplierImpl(final String token) {
        this.token = AsyncUtils.successful(token);
    }

    @Override
    public CompletionStage<String> get() {
        return token;
    }

    @Override
    protected void internalClose() {
    }
}
