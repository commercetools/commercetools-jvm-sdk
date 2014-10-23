package io.sphere.sdk.products.queries.search;

public interface FilterExpression<T> {
    /**
     * returns a filter expression.
     * Example: variants.attributes.color:"green","yellow"
     * @return String with unescaped sphere filter expression
     */
    String toSphereFilter();

    public static <T> FilterExpression<T> of(final String sphereFilterExpression) {
        return new SimpleFilterExpression<>(sphereFilterExpression);
    }
}
