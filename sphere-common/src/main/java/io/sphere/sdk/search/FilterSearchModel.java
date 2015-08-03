package io.sphere.sdk.search;

import javax.annotation.Nullable;

import static java.util.Collections.singletonList;

public class FilterSearchModel<T, V> extends SearchModelImpl<T> {
    protected TypeSerializer<V> typeSerializer;

    FilterSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment, final TypeSerializer<V> typeSerializer) {
        super(parent, pathSegment);
        this.typeSerializer = typeSerializer;
    }

    /**
     * Generates an expression to select all elements with the given attribute value.
     * For example: filtering by "red" would select only those elements with "red" value.
     * @param value the value to filter by
     * @return a filter expression for the given value
     */
    public FilterExpression<T> by(final V value) {
        return by(singletonList(value));
    }

    /**
     * Generates an expression to select all elements with the given attribute values.
     * For example: filtering by ["red", "blue"] would select only those elements with either "red" or "blue" value.
     * @param values the values to filter by
     * @return a filter expression for the given values
     */
    public FilterExpression<T> by(final Iterable<V> values) {
        return new TermFilterExpression<>(this, typeSerializer, values);
    }
}
