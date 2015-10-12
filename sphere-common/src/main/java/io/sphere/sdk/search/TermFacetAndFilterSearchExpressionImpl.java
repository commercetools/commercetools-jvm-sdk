package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

import java.util.List;

class TermFacetAndFilterSearchExpressionImpl<T> extends Base implements TermFacetAndFilterSearchExpression<T> {
    private final TermFacetExpression<T> facetExpression;
    private final List<FilterExpression<T>> filterExpressions;

    TermFacetAndFilterSearchExpressionImpl(final TermFacetExpression<T> facetExpression, final List<FilterExpression<T>> filterExpressions) {
        this.facetExpression =  facetExpression;
        this.filterExpressions = filterExpressions;
    }

    @Override
    public TermFacetExpression<T> facetExpression() {
        return facetExpression;
    }

    @Override
    public List<FilterExpression<T>> filterExpressions() {
        return filterExpressions;
    }
}
