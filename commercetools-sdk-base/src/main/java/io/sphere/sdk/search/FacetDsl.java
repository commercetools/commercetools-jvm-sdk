package io.sphere.sdk.search;

import java.util.List;

public interface FacetDsl<T, C> extends FacetSupport<T> {

    /**
     * Returns a new object with the new facet expression list as facets (query parameter {@code facet}).
     * @param facetExpressions the new facet expression list
     * @return a new object with facets
     */
    C withFacets(final List<FacetExpression<T>> facetExpressions);

    /**
     * Returns a new object with the new facet expression as facets (query parameter {@code facet}).
     * @param facetExpression the new facet expression
     * @return a new object with facets
     */
    C withFacets(final FacetExpression<T> facetExpression);

    /**
     * Returns a ResourceSearch with the new facet expression list appended to the existing facets (query parameter {@code facet}).
     * @param facetExpressions the new facet expression list
     * @return a ResourceSearch with the existing facets plus the new facet list.
     */
    C plusFacets(final List<FacetExpression<T>> facetExpressions);

    /**
     * Returns a new object with the new facet expression appended to the existing facets (query parameter {@code facet}).
     * @param facetExpression the new facet expression
     * @return a new object with the existing facets plus the new facet.
     */
    C plusFacets(final FacetExpression<T> facetExpression);

}
