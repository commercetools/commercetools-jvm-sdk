package io.sphere.sdk.search;

final class SimpleFilterExpression<T> extends SimpleBaseExpression implements FilterExpression<T> {
    private final String sphereFilterExpression;

    SimpleFilterExpression(final String sphereFilterExpression) {
        this.sphereFilterExpression = sphereFilterExpression;
    }

    @Override
    protected String pattern() {
        return "^(?<attribute>[^:\\s]*)(?<value>:(range)?.*)$";
    }

    @Override
    public String expression() {
        return sphereFilterExpression;
    }
}