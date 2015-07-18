package io.sphere.sdk.search;

/**
 * Search results can optionally be filtered and these filters can be applied in a few different scopes.
 * @param <T> Type of the resource for the filtered search
 */
public interface FilterExpression<T> {

    /**
     * returns a filter expression.
     * Example: variants.attributes.color:"green","yellow"
     * @return String with unescaped sphere filter expression
     */
    String toSphereFilter();

    static <T> FilterExpression<T> of(final String sphereFilterExpression) {
        return new SimpleFilterExpression<>(sphereFilterExpression);
    }
}
