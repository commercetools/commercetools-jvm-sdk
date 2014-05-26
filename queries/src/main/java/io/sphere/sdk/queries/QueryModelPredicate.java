package io.sphere.sdk.queries;

public abstract class QueryModelPredicate<T> extends PredicateBase<T> {
    private final QueryModel<T> queryModel;

    protected QueryModelPredicate(QueryModel<T> queryModel) {
        this.queryModel = queryModel;
    }

    @Override
    public final String toSphereQuery() {
        return buildQuery(queryModel, render());
    }

    protected abstract String render();

    protected QueryModel<T> getQueryModel() {
        return queryModel;
    }
}