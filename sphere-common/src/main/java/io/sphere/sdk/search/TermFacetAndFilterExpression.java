package io.sphere.sdk.search;

import java.util.List;

/**
 * Facets calculate statistical counts to aid in faceted navigation.
 * Example: variants.attributes.color:"green","yellow"
 * @param <T> Type of the resource for the facet
 */
public interface TermFacetAndFilterExpression<T> extends FacetAndFilterExpression<T> {

    TermFacetExpression<T> facetExpression();

    List<FilterExpression<T>> filterExpressions();

    static <T> TermFacetAndFilterExpression<T> of(final TermFacetExpression<T> facetExpression, List<FilterExpression<T>> filterExpressions) {
        return new TermFacetAndFilterExpressionImpl<>(facetExpression, filterExpressions);
    }
}
