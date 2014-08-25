package io.sphere.sdk.queries;

import com.google.common.collect.Iterables;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

class IsInPredicate<T, V, M> extends QueryModelPredicate<M> {
    private final Iterable<V> values;

    /**
     * Creates a predicate which is a shortcut for multiple or statements.
     * @param queryModel the parent model
     * @param values possible values to query for, if it is for Strings, the may need to be escaped concerning double quotes.
     */
    public IsInPredicate(final QueryModel<M> queryModel, final Iterable<V> values) {
        super(queryModel);
        if (Iterables.isEmpty(values)) {
            throw new IllegalArgumentException("Values must be a non empty list.");
        }
        this.values = values;
    }

    @Override
    protected String render() {
        return " in (\"" + Stream.of(values).map(i -> i.toString()).collect(joining("\", \"")) + "\")";
    }
}
