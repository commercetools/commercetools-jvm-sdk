package io.sphere.sdk.search;

import java.util.List;

public interface FilterDsl<T, C> extends FilterSupport<T> {

    /**
     * Returns a new object with the new result filter expression list as result filter (query parameter {@code filter}).
     * @param filterExpressions the new result filter expression list
     * @return a new object with {@code filterExpressions} as {@code resultFilters}
     */
    C withResultFilters(final List<FilterExpression<T>> filterExpressions);

    /**
     * Returns a new object with the new result filter expression list appended to the existing result filters (query parameter {@code filter}).
     * @param filterExpressions the new result filter expression list
     * @return a new object with the existing {@code resultFilters} plus the new {@code filterExpressions}.
     */
    C plusResultFilters(final List<FilterExpression<T>> filterExpressions);

    /**
     * Returns a new object with the new query filter expression list as query filters (query parameter {@code filter.query}).
     * @param filterExpressions the new query filter expression list
     * @return a new object with {@code queryFilters} as {@code filterExpressions}
     */
    C withQueryFilters(final List<FilterExpression<T>> filterExpressions);

    /**
     * Returns a new object with the new query filter expression list appended to the existing query filters (query parameter {@code filter.query}).
     * @param filterExpressions the new query filter expression list
     * @return a new object with the existing {@code queryFilters} plus the new {@code filterExpressions}.
     */
    C plusQueryFilters(final List<FilterExpression<T>> filterExpressions);

    /**
     * Returns a new object with the new facet filter list as facet filter (query parameter {@code filter.facet}).
     * @param filterExpressions the new facet filter expression list
     * @return a new object with {@code facetFilters} as {@code filterExpressions}
     */
    C withFacetFilters(final List<FilterExpression<T>> filterExpressions);

    /**
     * Returns a new object with the new facet filter list appended to the existing facet filters (query parameter {@code filter.facet}).
     * @param filterExpressions the new facet filter expression list
     * @return a new object with the existing {@code facetFilters} plus the new {@code filterExpressions}.
     */
    C plusFacetFilters(final List<FilterExpression<T>> filterExpressions);
}
