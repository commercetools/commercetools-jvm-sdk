package io.sphere.sdk.search;

import java.util.List;

/**
 * Bundle of a facet and filter expressions, intended to be used for {@code Faceted Search}.
 * @param <T> Type of the resource for the facet and filters
 *
 * @see io.sphere.sdk.search.model.FacetedSearchSearchModel
 */
public interface FacetedSearchExpression<T> {

    FacetExpression<T> facetExpression();

    List<FilterExpression<T>> filterExpressions();
}
