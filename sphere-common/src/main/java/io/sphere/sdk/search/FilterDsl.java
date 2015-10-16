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

    /**
     * Returns a new object with the new result filter expression as result filter (query parameter {@code filter}).
     * @param filterExpression the new result filter expression
     * @return a new object with {@code filterExpression} as {@code resultFilters}
     * @deprecated use {@link FilterDsl#withResultFilters(List)} instead
     */
    @Deprecated
    C withResultFilters(final FilterExpression<T> filterExpression);

    /**
     * Returns a new object with the new result filter expression appended to the existing result filters (query parameter {@code filter}).
     * @param filterExpression the new result filter expression
     * @return a new object with the existing {@code resultFilters} plus the new {@code filterExpression}.
     * @deprecated use {@link FilterDsl#plusResultFilters(List)} instead
     */
    @Deprecated
    C plusResultFilters(final FilterExpression<T> filterExpression);

    /**
     * Returns a new object with the new query filter expression as query filters (query parameter {@code filter.query}).
     * @param filterExpression the new query filter expression
     * @return a new object with {@code queryFilters} as {@code filterExpression}
     * @deprecated use {@link FilterDsl#withQueryFilters(List)} instead
     */
    @Deprecated
    C withQueryFilters(final FilterExpression<T> filterExpression);

    /**
     * Returns a new object with the new query filter expression appended to the existing query filters (query parameter {@code filter.query}).
     * @param filterExpression the new query filter expression
     * @return a new object with the existing {@code queryFilters} plus the new {@code filterExpression}
     * @deprecated use {@link FilterDsl#plusQueryFilters(List)} instead
     */
    @Deprecated
    C plusQueryFilters(final FilterExpression<T> filterExpression);

    /**
     * Returns a new object with the new facet filter as facet filter (query parameter {@code filter.facet}).
     * @param filterExpression the new facet filter expression
     * @return a new object with {@code facetFilters} as {@code filterExpression}
     * @deprecated use {@link FilterDsl#withFacetFilters(List)} instead
     */
    @Deprecated
    C withFacetFilters(final FilterExpression<T> filterExpression);

    /**
     * Returns a new object with the new facet filter appended to the existing facet filters (query parameter {@code filter.facet}).
     * @param filterExpression the new facet filter expression
     * @return a new object with the existing {@code facetFilters} plus the new {@code filterExpression}
     * @deprecated use {@link FilterDsl#plusFacetFilters(List)} instead
     */
    @Deprecated
    C plusFacetFilters(final FilterExpression<T> filterExpression);
}
