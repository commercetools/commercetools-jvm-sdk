package io.sphere.sdk.search;

final class SimpleFilterExpression<T> extends SimpleBaseExpression implements FilterExpression<T> {
    private final String sphereFilterExpression;

    SimpleFilterExpression(final String sphereFilterExpression) {
        this.sphereFilterExpression = sphereFilterExpression;
    }

    @Override
    public String toSearchExpression() {
        return sphereFilterExpression;
    }
}