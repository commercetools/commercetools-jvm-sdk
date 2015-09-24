package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

import java.util.List;

final class SimpleFacetedSearchExpression<T> extends Base implements FacetedSearchExpression<T> {
    private final String sphereFacetExpression;
    private final List<String> sphereFilterExpression;

    SimpleFacetedSearchExpression(final String sphereFacetExpression, final List<String> sphereFilterExpression) {
        this.sphereFacetExpression = sphereFacetExpression;
        this.sphereFilterExpression = sphereFilterExpression;
    }

    @Override
    public String toFacetExpression() {
        return sphereFacetExpression;
    }

    @Override
    public List<String> toFilterExpression() {
        return sphereFilterExpression;
    }
}