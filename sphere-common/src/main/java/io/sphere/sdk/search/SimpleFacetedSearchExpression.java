package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

import java.util.List;

import static java.util.stream.Collectors.toList;

final class SimpleFacetedSearchExpression<T> extends Base implements FacetedSearchExpression<T> {
    private final FacetExpression<T> facetExpression;
    private final List<FilterExpression<T>> filterExpressions;

    SimpleFacetedSearchExpression(final FacetExpression<T> facetExpression, final List<FilterExpression<T>> filterExpressions) {
        this.facetExpression = facetExpression;
        this.filterExpressions = filterExpressions;
    }

    @Override
    public FacetExpression<T> facetExpression() {
        return facetExpression;
    }

    @Override
    public List<FilterExpression<T>> filterExpressions() {
        return filterExpressions;
    }
}