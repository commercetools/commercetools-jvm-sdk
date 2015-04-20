package io.sphere.sdk.queries;

class IsLessThanOrEqualsPredicate<T, V, M> extends ComparisonPredicate<T, V, M> {

    public IsLessThanOrEqualsPredicate(final QueryModel<M> queryModel, final V value) {
        super(queryModel, value);
    }

    @Override
    protected String sign() {
        return "<=";
    }
}
