package io.sphere.sdk.queries;

public abstract class QueryModelQueryPredicate<T> extends QueryPredicateBase<T> {
    private final QueryModel<T> queryModel;

    protected QueryModelQueryPredicate(QueryModel<T> queryModel) {
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