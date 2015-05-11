package io.sphere.sdk.queries;

class IsGreaterThanOrEqualsQueryPredicate<T, V, M> extends ComparisonQueryPredicate<T, V, M> {

    public IsGreaterThanOrEqualsQueryPredicate(final QueryModel<M> queryModel, final V value) {
        super(queryModel, value);
    }

    @Override
    protected String sign() {
        return ">=";
    }
}
