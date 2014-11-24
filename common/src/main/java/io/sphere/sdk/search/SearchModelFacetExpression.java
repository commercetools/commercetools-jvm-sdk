package io.sphere.sdk.search;

public abstract class SearchModelFacetExpression<T> extends FacetExpressionBase<T> {
    private final SearchModel<T> searchModel;

    protected SearchModelFacetExpression(SearchModel<T> searchModel) {
        this.searchModel = searchModel;
    }

    @Override
    public final String toSphereFacet() {
        return buildQuery(searchModel, render());
    }

    protected abstract String render();

    protected SearchModel<T> getSearchModel() {
        return searchModel;
    }
}