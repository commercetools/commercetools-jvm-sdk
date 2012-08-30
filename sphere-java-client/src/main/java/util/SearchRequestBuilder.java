package de.commercetools.sphere.client.util;

import de.commercetools.sphere.client.BackendException;
import com.google.common.util.concurrent.ListenableFuture;

/** Represents a search request to the Sphere backend.
 *  Use {@link #fetch} or {@link #fetchAsync} to execute the request. */
public interface SearchRequestBuilder<T> {
    /** Executes the request to the Sphere backend and returns result. */
    public T fetch() throws BackendException;

    /** Creates a future that allows you to be notified when the results
     *  from the Sphere backend arrived.
     *  Does not make a request immediately.
     *  To be notified, add a listener to the future. */
    public ListenableFuture<T> fetchAsync() throws BackendException;
}
