package io.sphere.sdk.products.queries.search;

public interface Filter<T> {
    /**
     * returns a filter expression.
     * Example: variants.attributes.color:"green","yellow"
     * @return String with unescaped sphere filter expression
     */
    String toSphereFilter();

    public static <T> Filter<T> of(final String sphereFilterExpression) {
        return new SimpleFilter<>(sphereFilterExpression);
    }
}
