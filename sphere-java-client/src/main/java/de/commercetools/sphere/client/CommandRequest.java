package de.commercetools.sphere.client;

import com.google.common.util.concurrent.ListenableFuture;

/** Represents a commands to the Sphere backend. */
public interface CommandRequest<T> {
    /** Executes the request to the Sphere backend and returns a result. */
    T execute();

    /** Executes the request in a non-blocking way and returns a future that provides a notification
     *  when the results from the Sphere backend arrive. */
    ListenableFuture<T> executeAsync();
}
