package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

import java.util.List;

import static java.util.stream.Collectors.toList;

final class SimpleFacetedSearchExpression<T> extends Base implements FacetedSearchExpression<T> {
    private final String sphereFacetExpression;
    private final List<String> sphereFilterExpressions;

    SimpleFacetedSearchExpression(final String sphereFacetExpression, final List<String> sphereFilterExpressions) {
        this.sphereFacetExpression = sphereFacetExpression;
        this.sphereFilterExpressions = sphereFilterExpressions;
    }

    @Override
    public FacetExpression<T> facetExpression() {
        return new SimpleFacetExpression<>(sphereFacetExpression);
    }

    @Override
    public List<FilterExpression<T>> filterExpressions() {
        return sphereFilterExpressions.stream()
                .map(expr -> new SimpleFilterExpression<T>(expr))
                .collect(toList());
    }
}