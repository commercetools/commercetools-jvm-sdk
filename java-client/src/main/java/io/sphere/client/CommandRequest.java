package io.sphere.client;

import com.google.common.util.concurrent.ListenableFuture;

/** Request that sends a commands to the Sphere backend. */
public interface CommandRequest<T> {
    /** Executes the request and returns the result. */
    T execute();

    /** Executes the request asynchronously and returns a future providing the result. */
    ListenableFuture<SphereResult<T>> executeAsync();
}
