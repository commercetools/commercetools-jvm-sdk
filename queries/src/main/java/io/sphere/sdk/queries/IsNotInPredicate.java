package io.sphere.sdk.queries;

public class IsNotInPredicate<T, V, M> extends IsInPredicate<T, V, M> {
    public IsNotInPredicate(final QueryModel<M> queryModel, final Iterable<V> values) {
        super(queryModel, values);
    }

    @Override
    protected String render() {
        return " not" + super.render();
    }
}
