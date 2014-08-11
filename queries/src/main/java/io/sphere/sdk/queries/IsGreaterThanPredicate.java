package io.sphere.sdk.queries;

public class IsGreaterThanPredicate<T, V, M> extends ComparisonPredicate<T, V, M> {

    public IsGreaterThanPredicate(final QueryModel<M> queryModel, final V value) {
        super(queryModel, value);
    }

    @Override
    protected String sign() {
        return ">";
    }
}
