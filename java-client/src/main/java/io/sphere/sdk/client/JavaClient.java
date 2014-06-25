package io.sphere.sdk.client;

import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.sdk.requests.ClientRequest;

public interface JavaClient extends AutoCloseable {
    <T> ListenableFuture<T> execute(final ClientRequest<T> clientRequest);

    void close();
}
