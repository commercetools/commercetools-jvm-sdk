package io.sphere.sdk.search;

final class SimpleRangeFacetExpression<T> extends SimpleFacetExpression<T> implements RangeFacetExpression<T> {

    SimpleRangeFacetExpression(final String sphereFacetExpression) {
        super(sphereFacetExpression);
    }

    @Override
    protected String pattern() {
        return "^(?<attribute>[^:\\s]*)(?<value>:range.*?)( as (?<alias>.*))?$";
    }
}