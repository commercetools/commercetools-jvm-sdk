package io.sphere.sdk.search;

import javax.annotation.Nullable;

/**
 * Search expressions to use in a search request.
 * @param <T> Type of the resource for the search
 */
public interface SearchExpression<T> {

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

    /**
     * Generates the value applied to the search expression.
     * Example: range(0 to *)
     * @return the value of the expression
     */
    @Nullable
    String value();

}
