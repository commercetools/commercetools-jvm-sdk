package io.sphere.sdk.client;

import java.util.concurrent.ExecutionException;

public final class TestClient {
    private final SphereClient underlying;

    public TestClient(final SphereClient underlying) {
        this.underlying = underlying;
    }

    public <T> T execute(final SphereRequest<T> sphereRequest) {
        try {
            return underlying.execute(sphereRequest).toCompletableFuture().get();
        } catch (final InterruptedException | ExecutionException e) {
            throw (e.getCause() instanceof RuntimeException) ? ((RuntimeException) e.getCause()) : new TestClientException(e);
        }
    }

    public void close() {
        underlying.close();
    }

    public SphereClient getUnderlying() {
        return underlying;
    }
}
