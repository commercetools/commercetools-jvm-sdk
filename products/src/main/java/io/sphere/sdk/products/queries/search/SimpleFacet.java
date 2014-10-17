package io.sphere.sdk.products.queries.search;

final class SimpleFacet<T> extends FacetBase<T> {
    private final String sphereFacetExpression;

    SimpleFacet(final String sphereFacetExpression) {
        this.sphereFacetExpression = sphereFacetExpression;
    }

    @Override
    public String toSphereFacet() {
        return sphereFacetExpression;
    }
}
