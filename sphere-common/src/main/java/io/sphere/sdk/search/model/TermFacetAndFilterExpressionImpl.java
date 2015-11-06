package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.TermFacetAndFilterExpression;
import io.sphere.sdk.search.TermFacetExpression;

import java.util.List;

class TermFacetAndFilterExpressionImpl<T> implements TermFacetAndFilterExpression<T> {
    private final TermFacetExpression<T> termFacetExpression;
    private final List<FilterExpression<T>> filterExpressions;

    TermFacetAndFilterExpressionImpl(final TermFacetExpression<T> termFacetExpression, final List<FilterExpression<T>> filterExpressions) {
        this.termFacetExpression = termFacetExpression;
        this.filterExpressions = filterExpressions;
    }

    @Override
    public TermFacetExpression<T> facetExpression() {
        return termFacetExpression;
    }

    @Override
    public List<FilterExpression<T>> filterExpressions() {
        return filterExpressions;
    }
}
