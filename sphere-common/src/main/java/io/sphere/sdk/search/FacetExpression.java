package io.sphere.sdk.search;

/**
 * Facets calculate statistical counts to aid in faceted navigation.
 * Example: variants.attributes.color:"green","yellow"
 * @param <T> Type of the resource for the faceted search
 */
public interface FacetExpression<T> extends SearchExpression<T> {

    /**
     * Gets the path of the facet result, which is either the alias or the search path if no alias defined.
     * Example: variants.attributes.color
     * @return the path to access the facet result.
     */
    String resultPath();

    static <T> FacetExpression<T> of(final String sphereFacetExpression) {
        return new SimpleFacetExpression<>(sphereFacetExpression);
    }
}
