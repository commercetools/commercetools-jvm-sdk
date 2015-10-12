package io.sphere.sdk.search;

import java.util.List;

/**
 * Faceted search expressions, contains both a facet expression and a list of filter expressions.
 * Example: facet of variants.attributes.height:range(0 to *) and filtering variants.attributes.height:range(4 to 50)
 * @param <T> Type of the resource for the faceted search
 */
public interface RangeFacetAndFilterSearchExpression<T> extends FacetAndFilterSearchExpression<T> {

    /**
     * Returns a facet expression.
     * @return facet expression
     */
    RangeFacetExpression<T> facetExpression();

    static <T> RangeFacetAndFilterSearchExpression<T> of(final RangeFacetExpression<T> facetExpression, final List<FilterExpression<T>> filterExpressions) {
        return new RangeFacetAndFilterSearchExpressionImpl<>(facetExpression, filterExpressions);
    }
}
