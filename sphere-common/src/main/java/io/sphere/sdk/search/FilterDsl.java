package io.sphere.sdk.search;

import java.util.List;

public interface FilterDsl<T, C> extends FilterSupport<T> {

    /**
     * Returns a new object with the new result filter expression list as result filter (query parameter {@code filter}).
     * @param filterExpressions the new result filter expression list
     * @return a new object with resultFilters
     */
    C withResultFilters(final List<FilterExpression<T>> filterExpressions);

    /**
     * Returns a new object with the new result filter expression list appended to the existing result filters (query parameter {@code filter}).
     * @param filterExpressions the new result filter expression list
     * @return a new object with the existing result filter plus the new result filter list.
     */
    C plusResultFilters(final List<FilterExpression<T>> filterExpressions);

    /**
     * Returns a new object with the new query filter expression list as query filters (query parameter {@code filter.query}).
     * @param filterExpressions the new query filter expression list
     * @return a new object with queryFilters
     */
    C withQueryFilters(final List<FilterExpression<T>> filterExpressions);

    /**
     * Returns a new object with the new query filter expression list appended to the existing query filters (query parameter {@code filter.query}).
     * @param filterExpressions the new query filter expression list
     * @return a new object with the existing query filters plus the new query filter list.
     */
    C plusQueryFilters(final List<FilterExpression<T>> filterExpressions);

    /**
     * Returns a new object with the new facet filter list as facet filter (query parameter {@code filter.facet}).
     * @param filterExpressions the new facet filter expression list
     * @return a new object with facetFilters
     */
    C withFacetFilters(final List<FilterExpression<T>> filterExpressions);

    /**
     * Returns a new object with the new facet filter list appended to the existing facet filters (query parameter {@code filter.facet}).
     * @param filterExpressions the new facet filter expression list
     * @return a new object with the existing facet filters plus the new facet filter list.
     */
    C plusFacetFilters(final List<FilterExpression<T>> filterExpressions);

}
