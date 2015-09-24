package io.sphere.sdk.search;

/**
 * Facets calculate statistical counts to aid in faceted navigation.
 * Example: variants.attributes.color:"green","yellow"
 * @param <T> Type of the resource for the faceted search
 */
public interface FacetExpression<T> extends SearchExpression<T> {

    static <T> FacetExpression<T> of(final String sphereFacetExpression) {
        return new SimpleFacetExpression<>(sphereFacetExpression);
    }
}
