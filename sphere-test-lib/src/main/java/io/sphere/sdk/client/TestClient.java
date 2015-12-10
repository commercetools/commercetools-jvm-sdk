package io.sphere.sdk.client;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

public final class TestClient implements BlockingSphereClient {
    private final BlockingSphereClient delegate;

    public TestClient(final SphereClient underlying) {
        this.delegate = BlockingSphereClient.of(underlying, 20, TimeUnit.SECONDS);
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
    public <T> T executeBlocking(final SphereRequest<T> sphereRequest) {
        return delegate.executeBlocking(sphereRequest);
    }

    @Override
    public <T> T executeBlocking(final SphereRequest<T> sphereRequest, final long timeout, final TimeUnit unit) {
        return delegate.executeBlocking(sphereRequest, timeout, unit);
    }
}
