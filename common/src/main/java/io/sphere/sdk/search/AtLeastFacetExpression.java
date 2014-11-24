package io.sphere.sdk.search;

class AtLeastFacetExpression<T, V, M> extends ComparisonFacetExpression<T, V, M> {

    public AtLeastFacetExpression(final SearchModel<M> searchModel, final V value) {
        super(searchModel, value);
    }

    @Override
    protected String sign() {
        return ">=";
    }
}
