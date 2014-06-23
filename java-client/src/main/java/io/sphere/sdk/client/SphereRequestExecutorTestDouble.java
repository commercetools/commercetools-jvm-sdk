package io.sphere.sdk.client;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

public abstract class SphereRequestExecutorTestDouble implements SphereRequestExecutor {

    @Override
    public <T> ListenableFuture<T> execute(final ClientRequest<T> clientRequest) {
        return Futures.immediateFuture(result(clientRequest));
    }

    protected  <T> T result(@SuppressWarnings("unused") final ClientRequest<T> requestable) {
        throw new UnsupportedOperationException("override the " + SphereRequestExecutorTestDouble.class.getName() +
                ".result(final ClientRequest<T> requestable) method to return a value.");
    }

    @Override
    public void close() {
    }
}
