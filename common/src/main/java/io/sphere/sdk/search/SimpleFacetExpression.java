package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

final class SimpleFacetExpression<T> extends Base implements FacetExpression<T> {
    private final String sphereFacetExpression;

    SimpleFacetExpression(final String sphereFacetExpression) {
        this.sphereFacetExpression = sphereFacetExpression;
    }

    @Override
    public String toSphereFacet() {
        return sphereFacetExpression;
    }
}