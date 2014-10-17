package io.sphere.sdk.products.queries.search;

final class SimpleFilter<T> extends FilterBase<T> {
    private final String sphereFilterExpression;

    SimpleFilter(final String sphereFilterExpression) {
        this.sphereFilterExpression = sphereFilterExpression;
    }

    @Override
    public String toSphereFilter() {
        return sphereFilterExpression;
    }
}
