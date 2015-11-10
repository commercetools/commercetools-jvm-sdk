package io.sphere.sdk.search;

import java.util.List;

class RangeFacetAndFilterExpressionImpl<T> implements RangeFacetAndFilterExpression<T> {
    private final RangeFacetExpression<T> facetExpression;
    private final List<FilterExpression<T>> filterExpressions;

    RangeFacetAndFilterExpressionImpl(final RangeFacetExpression<T> facetExpression, final List<FilterExpression<T>> filterExpressions) {
        this.facetExpression = facetExpression;
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
