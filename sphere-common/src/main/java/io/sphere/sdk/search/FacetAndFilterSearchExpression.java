package io.sphere.sdk.search;

import java.util.List;

/**
 * Contains both a facet expression and a list of filter expressions.
 * Example: facet of variants.attributes.color and filtering variants.attributes.color:"green","yellow"
 * @param <T> Type of the resource for the faceted search
 */
public interface FacetAndFilterSearchExpression<T> {

    /**
     * Returns a facet expression.
     * @return facet expression
     */
    FacetExpression<T> facetExpression();

    /**
     * Returns a list of filter expressions.
     * @return List of filter expressions
     */
    List<FilterExpression<T>> filterExpressions();
}
