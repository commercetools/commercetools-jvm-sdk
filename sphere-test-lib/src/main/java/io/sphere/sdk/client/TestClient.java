package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class TestClient extends Base implements AutoCloseable {
    private final SphereClient underlying;

    public TestClient(final SphereClient underlying) {
        this.underlying = underlying;
    }

    public <T> T executeBlocking(final SphereRequest<T> sphereRequest) {
        try {
            return underlying.execute(sphereRequest).toCompletableFuture().get(20, TimeUnit.SECONDS);
        } catch (final InterruptedException | ExecutionException | TimeoutException e) {
            throw (e.getCause() instanceof RuntimeException) ? ((RuntimeException) e.getCause()) : new TestClientException(e);
        }
    }

    @Override
    public void close() {
        underlying.close();
    }

    public SphereClient getUnderlying() {
        return underlying;
    }
}
