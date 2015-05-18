package io.sphere.sdk.queries;

class IsLessThanOrEqualToQueryPredicate<T, V, M> extends ComparisonQueryPredicate<T, V, M> {

    public IsLessThanOrEqualToQueryPredicate(final QueryModel<M> queryModel, final V value) {
        super(queryModel, value);
    }

    @Override
    protected String sign() {
        return "<=";
    }
}
