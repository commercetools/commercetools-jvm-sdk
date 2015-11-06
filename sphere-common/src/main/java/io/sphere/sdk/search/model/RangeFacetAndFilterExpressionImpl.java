package io.sphere.sdk.search.model;

import io.sphere.sdk.search.*;

import java.util.List;

class RangeFacetAndFilterExpressionImpl<T> implements RangeFacetAndFilterExpression<T> {
    private final RangeFacetExpression<T> termFacetExpression;
    private final List<FilterExpression<T>> filterExpressions;

    RangeFacetAndFilterExpressionImpl(final RangeFacetExpression<T> termFacetExpression, final List<FilterExpression<T>> filterExpressions) {
        this.termFacetExpression = termFacetExpression;
        this.filterExpressions = filterExpressions;
    }

    @Override
    public RangeFacetExpression<T> facetExpression() {
        return termFacetExpression;
    }

    @Override
    public List<FilterExpression<T>> filterExpressions() {
        return filterExpressions;
    }
}
