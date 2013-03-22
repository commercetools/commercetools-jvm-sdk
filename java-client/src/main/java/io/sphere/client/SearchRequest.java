package io.sphere.client;

import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.client.facets.expressions.FacetExpression;
import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.model.SearchResult;

import java.util.Collection;

/** Represents a search request to the Sphere backend.
 *  Use {@link #fetch} or {@link #fetchAsync} to execute a request to backend. */
public interface SearchRequest<T> {
    /** Executes the request to the Sphere backend and returns a result. */
    SearchResult<T> fetch() throws SphereException;

    /** Executes the request in a non-blocking way and returns a future that provides a notification
     *  when the results from the Sphere backend arrive. */
    ListenableFuture<SearchResult<T>> fetchAsync() throws SphereException;

    /** Sets the page number for paging through results. Page numbers start at zero. */
    SearchRequest<T> page(int page);

    /** Sets the size of a page for paging through results. When page size is not set, the default of 10 is used. */
    SearchRequest<T> pageSize(int pageSize);

    /** Filters products by given constraints. */
    SearchRequest<T> filter(FilterExpression filter, FilterExpression... filters);

    /** Filters products by given constraints. */
    SearchRequest<T> filter(Iterable<FilterExpression> filters);

    /** Requests aggregated counts for given facet expressions. */
    SearchRequest<T> facet(FacetExpression facet, FacetExpression... facets);

    /** Requests aggregated counts for given facet expressions. */
    SearchRequest<T> facet(Iterable<FacetExpression> facets);

    /** Sorts products. When this method is not used, products are implicitly sorted by relevance.
     *
     * <p>Example: {@code sort(ProductSort.price.asc)}.
     *
     * @param sort Specifies how products are sorted. Use for example {@code ProductSort.price.asc}. */
    SearchRequest<T> sort(ProductSort sort);
}
