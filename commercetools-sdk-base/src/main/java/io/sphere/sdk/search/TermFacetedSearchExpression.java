package io.sphere.sdk.search;

import java.util.List;

/**
 * Facets calculate statistical counts to aid in faceted navigation.
 * Example: variants.attributes.color:"green","yellow"
 * @param <T> Type of the resource for the facet
 *
 * @see io.sphere.sdk.search.model.TermFacetedSearchSearchModel
 */
public interface TermFacetedSearchExpression<T> extends FacetedSearchExpression<T> {

    TermFacetExpression<T> facetExpression();

    List<FilterExpression<T>> filterExpressions();

    static <T> TermFacetedSearchExpression<T> of(final TermFacetExpression<T> facetExpression, List<FilterExpression<T>> filterExpressions) {
        return new TermFacetedSearchExpressionImpl<>(facetExpression, filterExpressions);
    }
}
