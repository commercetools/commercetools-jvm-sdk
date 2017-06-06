package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static io.sphere.sdk.client.SphereClientUtils.blockingWait;

final class BlockingSphereClientImpl extends Base implements BlockingSphereClient {

    private final SphereClient delegate;
    private final long defaultTimeout;
    private final TimeUnit unit;

    public BlockingSphereClientImpl(final SphereClient delegate, final long defaultTimeout, final TimeUnit unit) {
        this.delegate = delegate;
        this.defaultTimeout = defaultTimeout;
        this.unit = unit;
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
        return executeBlocking(sphereRequest, defaultTimeout, unit);
    }

    @Override
    public <T> T executeBlocking(final SphereRequest<T> sphereRequest, final long timeout, final TimeUnit unit) {
        final CompletionStage<T> completionStage = execute(sphereRequest);
        return blockingWait(completionStage, sphereRequest, timeout, unit);
    }

    @Override
    public <T> T executeBlocking(final SphereRequest<T> sphereRequest, final Duration duration) {
        return executeBlocking(sphereRequest, duration.toMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public SphereApiConfig getConfig() {
        return this.delegate.getConfig();
    }
}
