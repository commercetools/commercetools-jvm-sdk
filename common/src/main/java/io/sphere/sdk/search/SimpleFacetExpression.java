package io.sphere.sdk.search;

final class SimpleFacetExpression<T> extends FacetExpressionBase<T> {
    private final String sphereFacetExpression;

    SimpleFacetExpression(final String sphereFacetExpression) {
        this.sphereFacetExpression = sphereFacetExpression;
    }

    @Override
    public String toSphereFacet() {
        return sphereFacetExpression;
    }
}
