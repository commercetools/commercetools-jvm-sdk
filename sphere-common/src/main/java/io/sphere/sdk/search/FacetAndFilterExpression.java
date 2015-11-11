package io.sphere.sdk.search;

import java.util.List;

/**
 * Bundle of a facet and filter expressions, intended to be used for {@code Faceted Search}.
 * @param <T> Type of the resource for the facet and filters
 */
public interface FacetAndFilterExpression<T> {

    FacetExpression<T> facetExpression();

    List<FilterExpression<T>> filterExpressions();
}
