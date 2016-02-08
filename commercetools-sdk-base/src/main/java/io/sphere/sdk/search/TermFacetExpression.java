package io.sphere.sdk.search;

/**
 * Term facets calculate statistical counts for all values of an attribute.
 * Example: variants.attributes.color
 * @param <T> Type of the resource for the facet
 *
 * @see io.sphere.sdk.search.model.TermFacetSearchModel
 */
public interface TermFacetExpression<T> extends FacetExpression<T> {

    static <T> TermFacetExpression<T> of(final String sphereFacetExpression) {
        return new SimpleTermFacetExpression<>(sphereFacetExpression);
    }
}