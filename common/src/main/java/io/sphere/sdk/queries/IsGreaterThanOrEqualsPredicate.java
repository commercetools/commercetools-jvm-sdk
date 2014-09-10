package io.sphere.sdk.queries;

class IsGreaterThanOrEqualsPredicate<T, V, M> extends ComparisonPredicate<T, V, M> {

    public IsGreaterThanOrEqualsPredicate(final QueryModel<M> queryModel, final V value) {
        super(queryModel, value);
    }

    @Override
    protected String sign() {
        return ">=";
    }
}
