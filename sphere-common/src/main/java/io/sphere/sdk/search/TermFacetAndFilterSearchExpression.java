package io.sphere.sdk.search;

import java.util.List;

/**
 * Faceted search expressions, contains both a facet expression and a list of filter expressions.
 * Example: facet of variants.attributes.color and filtering variants.attributes.color:"green","yellow"
 * @param <T> Type of the resource for the faceted search
 */
public interface TermFacetAndFilterSearchExpression<T> extends FacetAndFilterSearchExpression<T> {

    /**
     * Returns a facet expression.
     * @return facet expression
     */
    TermFacetExpression<T> facetExpression();

    static <T> TermFacetAndFilterSearchExpression<T> of(final TermFacetExpression<T> facetExpression, final List<FilterExpression<T>> filterExpressions) {
        return new TermFacetAndFilterSearchExpressionImpl<>(facetExpression, filterExpressions);
    }
}
