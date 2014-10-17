package io.sphere.sdk.products.queries.search;

public interface Facet<T> {
    /**
     * returns a facet expression.
     * Example: variants.attributes.color as myColor
     * @return String with unescaped sphere facet expression
     */
    String toSphereFacet();

    public static <T> Facet<T> of(final String sphereFacetExpression) {
        return new SimpleFacet<>(sphereFacetExpression);
    }
}
