package io.sphere.sdk.search;

/**
 * Search results can optionally be filtered and these filters can be applied in a few different scopes.
 * Example: variants.attributes.color:"green","yellow"
 * @param <T> Type of the resource for the filtered search
 */
public interface FilterExpression<T> extends SearchExpression<T> {

    static <T> FilterExpression<T> of(final String sphereFilterExpression) {
        return new SimpleFilterExpression<>(sphereFilterExpression);
    }
}
