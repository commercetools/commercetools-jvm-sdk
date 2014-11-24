package io.sphere.sdk.search;

class IsGreaterThanFacetExpression<T, V, M> extends ComparisonFacetExpression<T, V, M> {

    public IsGreaterThanFacetExpression(final SearchModel<M> searchModel, final V value) {
        super(searchModel, value);
    }

    @Override
    protected String sign() {
        return ">";
    }
}
