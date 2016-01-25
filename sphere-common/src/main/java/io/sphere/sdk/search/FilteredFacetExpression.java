package io.sphere.sdk.search;

/**
 * Filtered facets calculate statistical count for all given values.
 * Example: variants.attributes.color:"red","green"
 * @param <T> Type of the resource for the facet
 *
 * @see io.sphere.sdk.search.model.TermFacetSearchModel
 */
public interface FilteredFacetExpression<T> extends FacetExpression<T> {

    static <T> FilteredFacetExpression<T> of(final String sphereFacetExpression) {
        return new SimpleFilteredFacetExpression<>(sphereFacetExpression);
    }
}
