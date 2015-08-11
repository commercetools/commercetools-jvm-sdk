package io.sphere.sdk.search;

final class SimpleFacetExpression<T> extends SimpleBaseExpression implements FacetExpression<T> {
    private final String sphereFacetExpression;

    SimpleFacetExpression(final String sphereFacetExpression) {
        this.sphereFacetExpression = sphereFacetExpression;
    }

    @Override
    protected String expression() {
        return sphereFacetExpression;
    }

    @Override
    public String toSphereFacet() {
        return sphereFacetExpression;
    }
}