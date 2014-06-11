package io.sphere.sdk.queries;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

public class IsInPredicate<T, V> extends QueryModelPredicate<T> {
    private final Iterable<V> values;

    /**
     * Creates a predicate which is a shortcut for multiple or statements.
     * @param queryModel the parent model
     * @param values possible values to query for, if it is for Strings, the may need to be escaped concerning double quotes.
     */
    public IsInPredicate(final QueryModel<T> queryModel, final Iterable<V> values) {
        super(queryModel);
        if (Iterables.isEmpty(values)) {
            throw new IllegalArgumentException("Values must be a non empty list.");
        }
        this.values = values;
    }

    @Override
    protected String render() {
        return " in (\"" + Joiner.on("\", \"").join(values) + "\")";
    }

    @Override
    public String toString() {
        return "IsInPredicate{" +
                "values=" + Iterables.toString(values) +
                '}';
    }
}
