package io.sphere.sdk.search;

class IsLessThanFacetExpression<T, V, M> extends ComparisonFacetExpression<T, V, M> {

    public IsLessThanFacetExpression(final SearchModel<M> searchModel, final V value) {
        super(searchModel, value);
    }

    @Override
    protected String sign() {
        return "<";
    }
}