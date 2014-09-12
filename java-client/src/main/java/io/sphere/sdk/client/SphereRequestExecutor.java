package io.sphere.sdk.client;

import io.sphere.sdk.http.ClientRequest;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;

public interface SphereRequestExecutor extends Closeable {
    <T> CompletableFuture<T> execute(final ClientRequest<T> clientRequest);

    void close();
}