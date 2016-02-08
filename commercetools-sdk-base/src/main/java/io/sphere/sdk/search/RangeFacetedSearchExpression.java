package io.sphere.sdk.search;

import java.util.List;

/**
 * Facets calculate statistical counts to aid in faceted navigation.
 * Example: variants.attributes.color:"green","yellow"
 * @param <T> Type of the resource for the facet
 *
 * @see io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel
 */
public interface RangeFacetedSearchExpression<T> extends FacetedSearchExpression<T> {

    RangeFacetExpression<T> facetExpression();

    List<FilterExpression<T>> filterExpressions();

    static <T> RangeFacetedSearchExpression<T> of(final RangeFacetExpression<T> facetExpression, List<FilterExpression<T>> filterExpressions) {
        return new RangeFacetedSearchExpressionImpl<>(facetExpression, filterExpressions);
    }
}
