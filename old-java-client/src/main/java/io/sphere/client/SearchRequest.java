package io.sphere.client;

import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.client.facets.expressions.FacetExpression;
import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.model.SearchResult;

/** Request that uses a Sphere search API to fetch objects satisfying some conditions (including fulltext search)
 * and provide faceting functionality. Search is currently only supported for products. */
public interface SearchRequest<T> {
    /** Executes the request and returns the result. */
    SearchResult<T> fetch();

    /** Executes the request asynchronously and returns a future providing the result. */
    ListenableFuture<SearchResult<T>> fetchAsync();

    /** Sets the page number for paging through results. Page numbers start at zero. */
    SearchRequest<T> page(int page);

    /** Sets the size of a page for paging through results. When page size is not set, the default of 25 is used. */
    SearchRequest<T> pageSize(int pageSize);

    /** Filters products by given constraints. */
    SearchRequest<T> filter(FilterExpression filter, FilterExpression... filters);

    /** Filters products by given constraints. */
    SearchRequest<T> filter(Iterable<FilterExpression> filters);

    /** Requests aggregated counts to be calculated for given facet expressions. */
    SearchRequest<T> facet(FacetExpression facet, FacetExpression... facets);

    /** Requests aggregated counts to be calculated for given facet expressions. */
    SearchRequest<T> facet(Iterable<FacetExpression> facets);

    /** Sorts products. When this method is not used, products are implicitly sorted by relevance.
     *
     * <p>Example: {@code sort(ProductSort.price.asc)}.
     *
     * @param sort Specifies how products should be sorted. */
    SearchRequest<T> sort(ProductSort sort);
}
