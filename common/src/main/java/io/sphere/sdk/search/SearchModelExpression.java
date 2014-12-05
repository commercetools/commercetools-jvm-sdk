package io.sphere.sdk.search;

abstract class SearchModelExpression<T> extends ExpressionBase<T> {
    private final SearchModel<T> searchModel;

    protected SearchModelExpression(SearchModel<T> searchModel) {
        this.searchModel = searchModel;
    }

    @Override
    public final String toSphereSearchExpression() {
        return buildQuery(searchModel, render());
    }

    protected abstract String render();

    protected SearchModel<T> getSearchModel() {
        return searchModel;
    }
}