package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import javax.annotation.Nullable;

import java.util.List;
import java.util.function.Function;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

abstract class FilterSearchModel<T, V> extends SearchModelImpl<T> {
    protected Function<V, String> typeSerializer;

    FilterSearchModel(@Nullable final SearchModel<T> parent, final Function<V, String> typeSerializer) {
        super(parent, null);
        this.typeSerializer = typeSerializer;
    }

    /**
     * Generates an expression to select all elements with the given attribute value.
     * For example: filtering by "red" would select only those elements with "red" value.
     * @param value the value to filter by
     * @return a filter expression for the given value
     */
    public List<FilterExpression<T>> by(final V value) {
        return singletonList(filterBy(value));
    }

    /**
     * Generates an expression to select all elements with attributes matching any of the given values.
     * For example: filtering by ["red", "blue"] would select only those elements with either "red" or "blue" value.
     * @param values the values to filter by
     * @return a filter expression for the given values
     */
    public List<FilterExpression<T>> byAny(final Iterable<V> values) {
        return singletonList(filterBy(values));
    }

    /**
     * Generates an expression to select all elements with attributes matching all the given values.
     * For example: filtering by ["red", "blue"] would select only those elements with both "red" and "blue" values.
     * @param values the values to filter by
     * @return a filter expression for the given values
     */
    public List<FilterExpression<T>> byAll(final Iterable<V> values) {
        return toStream(values)
                .map(value -> filterBy(value))
                .collect(toList());
    }

    /**
     * Generates an expression to select all elements with attributes matching any of the given values.
     * For example: filtering by ["red", "blue"] would select only those elements with either "red" or "blue" value.
     * @param values the values to filter by
     * @return a filter expression for the given values
     */
    public List<FilterExpression<T>> byAnyAsString(final Iterable<String> values) {
        return singletonList(filterByAsString(values));
    }

    /**
     * Generates an expression to select all elements with attributes matching all the given values.
     * For example: filtering by ["red", "blue"] would select only those elements with both "red" and "blue" values.
     * @param values the values to filter by
     * @return a filter expression for the given values
     */
    public List<FilterExpression<T>> byAllAsString(final Iterable<String> values) {
        return toStream(values)
                .map(value -> filterByAsString(value))
                .collect(toList());
    }

    private TermFilterExpression<T, V> filterBy(final V value) {
        return filterBy(singletonList(value));
    }

    private TermFilterExpression<T, V> filterBy(final Iterable<V> values) {
        return new TermFilterExpression<>(this, typeSerializer, values);
    }

    private TermFilterExpression<T, String> filterByAsString(final String value) {
        return filterByAsString(singletonList(value));
    }

    private TermFilterExpression<T, String> filterByAsString(final Iterable<String> values) {
        return new TermFilterExpression<>(this, TypeSerializer.ofString().getSerializer(), values);
    }
}
