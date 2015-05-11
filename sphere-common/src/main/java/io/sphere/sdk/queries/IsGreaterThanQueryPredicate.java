package io.sphere.sdk.queries;

class IsGreaterThanQueryPredicate<T, V, M> extends ComparisonQueryPredicate<T, V, M> {

    public IsGreaterThanQueryPredicate(final QueryModel<M> queryModel, final V value) {
        super(queryModel, value);
    }

    @Override
    protected String sign() {
        return ">";
    }
}
