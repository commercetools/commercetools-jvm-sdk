package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

import java.util.concurrent.CompletionStage;

/**
 * A public base class to decorate {@link SphereClient}s.
 */
public abstract class SphereClientDecorator extends Base implements SphereClient {
    private final SphereClient delegate;

    protected SphereClientDecorator(final SphereClient delegate) {
        this.delegate = delegate;
    }

    @Override
    public void close() {
        delegate.close();
    }

    @Override
    public <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest) {
        return delegate.execute(sphereRequest);
    }

    @Override
    public SphereApiConfig getConfig() {
        return delegate.getConfig();
    }
}
