package io.sphere.sdk.queries;

import java.util.StringJoiner;

import static io.sphere.sdk.utils.IterableUtils.requireNonEmpty;

final class ContainsAllPredicate<T, V, M> extends QueryModelQueryPredicate<M> {
    private final Iterable<V> values;

    public ContainsAllPredicate(final QueryModel<M> queryModel, final Iterable<V> values) {
        super(queryModel);
        requireNonEmpty(values);//SPHERE.IO requires values not to be empty
        this.values = values;
    }

    @Override
    protected String render() {
        final StringJoiner joiner = new StringJoiner(", ");
        values.forEach(x -> joiner.add(x.toString()));
        return " contains all (" + joiner.toString() + ")";
    }
}
