package io.sphere.sdk.queries;

public class IsLessThanPredicate<T, V, M> extends ComparisonPredicate<T, V, M> {

    public IsLessThanPredicate(final QueryModel<M> queryModel, final V value) {
        super(queryModel, value);
    }

    @Override
    protected String sign() {
        return "<";
    }
}