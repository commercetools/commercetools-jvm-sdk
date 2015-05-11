package io.sphere.sdk.queries;

class IsNotInQueryPredicate<T, V, M> extends IsInQueryPredicate<T, V, M> {
    public IsNotInQueryPredicate(final QueryModel<M> queryModel, final Iterable<V> values) {
        super(queryModel, values);
    }

    @Override
    protected String render() {
        return " not" + super.render();
    }
}
