package io.sphere.sdk.client;

import com.google.common.util.concurrent.ListenableFuture;

public interface JavaClient extends AutoCloseable {
    <T> ListenableFuture<T> execute(final ClientRequest<T> clientRequest);

    void close();
}
