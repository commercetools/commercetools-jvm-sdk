package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

import java.util.List;

import static java.util.stream.Collectors.toList;

class FacetedSearchExpressionImpl<T> extends Base implements FacetedSearchExpression<T> {
    private final FacetExpression<T> facetExpression;
    private final List<FilterExpression<T>> filterExpression;

    FacetedSearchExpressionImpl(final FacetExpression<T> facetExpression, final List<FilterExpression<T>> filterExpression) {
        this.facetExpression =  facetExpression;
        this.filterExpression = filterExpression;
    }

    @Override
    public String toFacetExpression() {
        return facetExpression.toSearchExpression();
    }

    @Override
    public List<String> toFilterExpression() {
        return filterExpression.stream()
                .map(expr -> expr.toSearchExpression())
                .collect(toList());
    }
}
