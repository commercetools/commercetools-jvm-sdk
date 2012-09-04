package de.commercetools.sphere.client.util;

import de.commercetools.sphere.client.BackendException;
import com.google.common.util.concurrent.ListenableFuture;

/** Represents a request to the Sphere backend.
 *  Use {@link #fetch} or {@link #fetchAsync} to execute the request. */
public interface RequestBuilder<T> {
    /** Executes the request to the Sphere backend and returns result. */
    T fetch() throws BackendException;

    /** Creates a future that allows you to be notified when the results from the Sphere backend arrived.
     *  Does not make a request immediately. To be notified, add a listener to the future. */
    ListenableFuture<T> fetchAsync() throws BackendException;

    /** Requests references to be expanded in the returned JSON documents.
     *  Expanded references contain the full target objects they link to.
     *
     *  @param paths The paths to be expanded, such as 'vendor', 'categories[*]' or 'variants[*].vendor'. */
    RequestBuilder<T> expand(String... paths);
}
