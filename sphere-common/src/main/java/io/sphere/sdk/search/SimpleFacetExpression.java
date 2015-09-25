package io.sphere.sdk.search;

final class SimpleFacetExpression<T> extends SimpleBaseExpression implements FacetExpression<T> {
    private final String sphereFacetExpression;

    SimpleFacetExpression(final String sphereFacetExpression) {
        this.sphereFacetExpression = sphereFacetExpression;
    }

    @Override
    public String expression() {
        return sphereFacetExpression;
    }
}