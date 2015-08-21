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
    String toSearchExpression();

    /**
     * Generates the attribute path for the facet.
     * Example: variants.attributes.color
     * @return the path for the attribute to be faceted
     */
    String attributePath();

    static <T> FacetExpression<T> of(final String sphereFacetExpression) {
        return new SimpleFacetExpression<>(sphereFacetExpression);
    }
}
