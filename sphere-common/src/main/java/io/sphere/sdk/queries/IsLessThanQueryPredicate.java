package io.sphere.sdk.queries;

class IsLessThanQueryPredicate<T, V, M> extends ComparisonQueryPredicate<T, V, M> {

    public IsLessThanQueryPredicate(final QueryModel<M> queryModel, final V value) {
        super(queryModel, value);
    }

    @Override
    protected String sign() {
        return "<";
    }
}