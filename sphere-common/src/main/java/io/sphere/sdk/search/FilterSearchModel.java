package io.sphere.sdk.search;

import javax.annotation.Nullable;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

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
        return new TermFilterExpression<>(this, typeSerializer, singletonList(value));
    }

    /**
     * Generates an expression to select all elements with any of the given attribute values.
     * For example: filtering by ["red", "blue"] would select only those elements with either "red" or "blue" value.
     * @param values the values to filter by
     * @return a filter expression for the given values
     */
    public FilterExpression<T> byAny(final Iterable<V> values) {
        return new TermFilterExpression<>(this, typeSerializer, values);
    }

    /**
     * Generates an expression to select all elements with all the given attribute values.
     * For example: filtering by ["red", "blue"] would select only those elements with both "red" and "blue" values.
     * @param values the values to filter by
     * @return a filter expression for the given values
     */
    public List<FilterExpression<T>> byAll(final Iterable<V> values) {
        return StreamSupport.stream(values.spliterator(), false)
                .map(value -> by(value))
                .collect(toList());
    }
}
