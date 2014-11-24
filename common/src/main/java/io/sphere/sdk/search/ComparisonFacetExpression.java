package io.sphere.sdk.search;

abstract class ComparisonFacetExpression<T, V, M> extends SearchModelFacetExpression<M> {
    private final V value;

    ComparisonFacetExpression(final SearchModel<M> searchModel, final V value) {
        super(searchModel);
        this.value = value;
    }

    @Override
    protected String render() {
        return " " + sign() + " " + value;
    }

    protected abstract String sign();
}
