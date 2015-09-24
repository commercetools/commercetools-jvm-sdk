package io.sphere.sdk.search;

import java.util.List;

/**
 * Faceted search expressions, contains both a filter and a facet expression.
 * Example: facet of variants.attributes.color and filtering variants.attributes.color:"green","yellow"
 * @param <T> Type of the resource for the faceted search
 */
public interface FacetedSearchExpression<T> {

    /**
     * Returns a facet expression.
     * @return String with unescaped sphere facet expression
     */
    String toFacetExpression();

    /**
     * Returns a list of filter expressions.
     * @return List of strings with unescaped sphere filter expressions
     */
    List<String> toFilterExpression();

    static <T> FacetedSearchExpression<T> of(final String sphereFacetExpression, final List<String> sphereFilterExpression) {
        return new SimpleFacetedSearchExpression<>(sphereFacetExpression, sphereFilterExpression);
    }
}
