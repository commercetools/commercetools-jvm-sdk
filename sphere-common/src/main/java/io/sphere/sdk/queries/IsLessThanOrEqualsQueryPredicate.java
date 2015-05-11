package io.sphere.sdk.queries;

class IsLessThanOrEqualsQueryPredicate<T, V, M> extends ComparisonQueryPredicate<T, V, M> {

    public IsLessThanOrEqualsQueryPredicate(final QueryModel<M> queryModel, final V value) {
        super(queryModel, value);
    }

    @Override
    protected String sign() {
        return "<=";
    }
}
