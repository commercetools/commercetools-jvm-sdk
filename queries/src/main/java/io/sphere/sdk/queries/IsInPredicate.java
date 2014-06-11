package io.sphere.sdk.queries;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;

import java.util.List;

public class IsInPredicate<T, V> extends QueryModelPredicate<T> {
    private final List<V> values;

    protected IsInPredicate(final QueryModel<T> queryModel, final List<V> values) {
        super(queryModel);
        if (values.size() < 1) {
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
