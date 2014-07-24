package io.sphere.sdk.client;

import io.sphere.sdk.requests.ClientRequest;

import java.util.concurrent.CompletableFuture;

public abstract class SphereRequestExecutorTestDouble implements SphereRequestExecutor {

    @Override
    public <T> CompletableFuture<T> execute(final ClientRequest<T> clientRequest) {
        return CompletableFuture.completedFuture(result(clientRequest));
    }

    protected  <T> T result(@SuppressWarnings("unused") final ClientRequest<T> requestable) {
        throw new UnsupportedOperationException("override the " + SphereRequestExecutorTestDouble.class.getName() +
                ".result(final ClientRequest<T> requestable) method to return a value.");
    }

    @Override
    public void close() {
    }
}
