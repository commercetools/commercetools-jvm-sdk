package io.sphere.sdk.search;

/**
 * Facets calculate statistical counts to aid in faceted navigation.
 * @param <T> Type of the resource for the faceted search
 */
public interface FacetExpression<T> {
    /**
     * returns a facet expression.
     * Example: variants.attributes.color as myColor
     * @return String with unescaped sphere facet expression
     */
    String toSphereFacet();

    static <T> FacetExpression<T> of(final String sphereFacetExpression) {
        return new SimpleFacetExpression<>(sphereFacetExpression);
    }
}
