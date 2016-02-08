package io.sphere.sdk.queries;

import java.util.StringJoiner;

import static io.sphere.sdk.utils.SphereInternalUtils.requireNonEmpty;

class IsInQueryPredicate<T, V, M> extends QueryModelQueryPredicate<M> {
    private final Iterable<V> values;

    /**
     * Creates a predicate which is a shortcut for multiple or statements.
     * @param queryModel the parent model
     * @param values possible values to query for, if it is for Strings, the may need to be escaped concerning double quotes.
     * @throws IllegalArgumentException if values is empty
     */
    public IsInQueryPredicate(final QueryModel<M> queryModel, final Iterable<V> values) {
        super(queryModel);
        requireNonEmpty(values);
        this.values = values;
    }

    @Override
    protected String render() {
        final StringJoiner joiner = new StringJoiner(", ");
        values.forEach(x -> joiner.add(x.toString()));
        return " in (" + joiner.toString() + ")";
    }
}
