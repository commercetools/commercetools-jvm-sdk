package io.sphere.sdk.search;

/**
 * Search expressions to use in a search request.
 * @param <T> Type of the resource for the search
 */
interface SearchExpression<T> {

    /**
     * Returns a search expression.
     * @return String with unescaped sphere search expression
     */
    String expression();

    /**
     * Generates the path for the attribute to apply the search expression.
     * Example: variants.attributes.color
     * @return the path for the attribute
     */
    String attributePath();
}
