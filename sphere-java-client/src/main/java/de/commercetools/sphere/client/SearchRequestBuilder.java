package de.commercetools.sphere.client;

import com.google.common.util.concurrent.ListenableFuture;
import de.commercetools.sphere.client.model.SearchResult;

import java.util.Collection;

/** Represents a search request to the Sphere backend.
 *  Use {@link #fetch} or {@link #fetchAsync} to execute a request to backend. */
public interface SearchRequestBuilder<T> {
    /** Executes the request to the Sphere backend and returns a result. */
    SearchResult<T> fetch() throws SphereException;

    /** Executes the request in a non-blocking way and returns a future that provides a notification
     *  when the results from the Sphere backend arrive. */
    ListenableFuture<SearchResult<T>> fetchAsync() throws SphereException;

    /** Sets the page number for paging through results. Page numbers start at zero. */
    SearchRequestBuilder<T> page(int page);

    /** Sets the size of a page for paging through results. When page size is not set, the default of 10 is used. */
    SearchRequestBuilder<T> pageSize(int pageSize);

    /** Requests references to be expanded in the returned JSON documents.
     *  Expanded references contain the full target objects they link to.
     *
     *  @param paths The paths to be expanded, e.g. 'vendor', 'categories[*]' or 'variants[*].vendor'. */
    SearchRequestBuilder<T> expand(String... paths);

    /** Requests aggregated counts for given facet expressions to be computed. */
    public SearchRequestBuilder<T> facets(FacetExpression... facets);

    /** Requests aggregated counts for given facet expressions to be computed. */
    public SearchRequestBuilder<T> facets(Collection<FacetExpression> filters);
}
