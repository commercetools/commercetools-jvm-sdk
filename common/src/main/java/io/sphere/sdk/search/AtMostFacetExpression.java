package io.sphere.sdk.search;

class AtMostFacetExpression<T, V, M> extends ComparisonFacetExpression<T, V, M> {

    public AtMostFacetExpression(final SearchModel<M> searchModel, final V value) {
        super(searchModel, value);
    }

    @Override
    protected String sign() {
        return "<=";
    }
}
