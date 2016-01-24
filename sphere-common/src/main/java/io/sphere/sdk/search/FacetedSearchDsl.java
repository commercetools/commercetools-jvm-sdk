package io.sphere.sdk.search;

import java.util.List;

public interface FacetedSearchDsl<T, C> extends FacetedSearchSupport<T> {

    /**
     * Returns a new object with the new faceted search expression list as faceted search (combination of the query parameter {@code facet}
     * with the facet expression and the query parameters {@code filter.facet} and {@code filter} with the filter expressions).
     * This enables the behaviour of the faceted search as explained in the <a href="{@docRoot}/io/sphere/sdk/meta/SearchDocumentation.html#facetedSearch">documentation</a>.
     * @param facetedSearchExpressions the new faceted search expression list
     * @return a new object with faceted search
     */
    C withFacetedSearch(final List<FacetedSearchExpression<T>> facetedSearchExpressions);

    /**
     * Returns a new object with the new faceted search expression as facets (combination of the query parameter {@code facet}
     * with the facet expression and the query parameters {@code filter.facet} and {@code filter} with the filter expressions).
     * This enables the behaviour of the faceted search as explained in the <a href="{@docRoot}/io/sphere/sdk/meta/SearchDocumentation.html#facetedSearch">documentation</a>.
     * @param facetedSearchExpression the new faceted search expression
     * @return a new object with faceted search
     */
    C withFacetedSearch(final FacetedSearchExpression<T> facetedSearchExpression);

    /**
     * Returns a ResourceSearch with the new faceted search expression list appended to the existing faceted search (combination of the query parameter {@code facet}
     * with the facet expression and the query parameters {@code filter.facet} and {@code filter} with the filter expressions).
     * This enables the behaviour of the faceted search as explained in the <a href="{@docRoot}/io/sphere/sdk/meta/SearchDocumentation.html#facetedSearch">documentation</a>.
     * @param facetedSearchExpressions the new faceted search expression list
     * @return a ResourceSearch with the existing faceted search plus the new faceted search list.
     */
    C plusFacetedSearch(final List<FacetedSearchExpression<T>> facetedSearchExpressions);

    /**
     * Returns a new object with the new faceted search expression appended to the existing faceted search (combination of the query parameter {@code facet}
     * with the facet expression and the query parameters {@code filter.facet} and {@code filter} with the filter expressions).
     * This enables the behaviour of the faceted search as explained in the <a href="{@docRoot}/io/sphere/sdk/meta/SearchDocumentation.html#facetedSearch">documentation</a>.
     * @param facetedSearchExpression the new faceted search expression
     * @return a new object with the existing faceted search plus the new facet.
     */
    C plusFacetedSearch(final FacetedSearchExpression<T> facetedSearchExpression);

}
