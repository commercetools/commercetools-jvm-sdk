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

    /** Requests references to be expanded in the returned JSON documents.
     *  Expanded references contain the full target objects they link to.
     *
     *  @param paths The paths to be expanded, e.g. 'vendor', 'categories[*]' or 'variants[*].vendor'. */
    public SearchRequestBuilder<T> expand(String... paths);

    /** Adds a filter. A filter filters the results of a query after facets have been calculated 
     * and thus does not influence facet counts.
     * 
     * @param path The path to be matched in the returned documents, e.g. 'categories.id'.
     * @param value The value to search for. */
    public SearchRequestBuilder<T> filter(String path, String value);

    /** Adds a filter. A filter filters the results of a query after facets have been calculated
     * and thus does not influence facet counts.
     *
     * @param path The path to be matched in the returned documents, e.g. 'categories.id'.
     * @param value The value to search for. */
    public SearchRequestBuilder<T> filter(String path, double value);

    /** Adds a filter. A filter filters the results of a query after facets have been calculated
     * and thus does not influence facet counts.
     *
     * @param path The path to be matched in the returned documents, e.g. 'categories.id'.
     * @param value The value to search for. */
    public SearchRequestBuilder<T> filter(String path, int value);
}
