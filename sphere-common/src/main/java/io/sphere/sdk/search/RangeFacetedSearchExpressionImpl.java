package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

import java.util.List;

class RangeFacetedSearchExpressionImpl<T> extends Base implements RangeFacetedSearchExpression<T> {
    private final RangeFacetExpression<T> facetExpression;
    private final List<FilterExpression<T>> filterExpressions;

    RangeFacetedSearchExpressionImpl(final RangeFacetExpression<T> facetExpression, final List<FilterExpression<T>> filterExpressions) {
        this.facetExpression =  facetExpression;
        this.filterExpressions = filterExpressions;
    }

    @Override
    public RangeFacetExpression<T> facetExpression() {
        return facetExpression;
    }

    @Override
    public List<FilterExpression<T>> filterExpressions() {
        return filterExpressions;
    }
}
