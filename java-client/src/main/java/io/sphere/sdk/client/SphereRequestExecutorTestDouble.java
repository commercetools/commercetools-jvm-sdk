package io.sphere.sdk.client;

import java.util.concurrent.CompletableFuture;

public abstract class SphereRequestExecutorTestDouble implements SphereRequestExecutor {

    @Override
    public <T> CompletableFuture<T> execute(final SphereRequest<T> sphereRequest) {
        return CompletableFuture.completedFuture(result(sphereRequest));
    }

    protected  <T> T result(@SuppressWarnings("unused") final SphereRequest<T> requestable) {
        throw new UnsupportedOperationException("override the " + SphereRequestExecutorTestDouble.class.getName() +
                ".result(final ClientRequest<T> requestable) method to return a value.");
    }

    @Override
    public void close() {
    }
}
