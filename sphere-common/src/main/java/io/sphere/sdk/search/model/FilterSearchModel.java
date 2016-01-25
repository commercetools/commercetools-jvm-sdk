package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import java.util.List;

/**
 * Model to build filters.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
public interface FilterSearchModel<T, V> {

    /**
     * Generates an expression to select all elements with the given attribute value.
     * For example: filtering by "red" would select only those elements with "red" value.
     * @param value the value to filter by
     * @return a filter expression for the given value
     */
    List<FilterExpression<T>> is(final V value);

    /**
     * @see #containsAny(Iterable)
     */
    default List<FilterExpression<T>> isIn(final Iterable<V> values) {
        return containsAny(values);
    }

    /**
     * Generates an expression to select all elements with attributes matching any of the given values.
     * For example: filtering by ["red", "blue"] would select only those elements with either "red" or "blue" value.
     * @param values the values to filter by
     * @return a filter expression for the given values
     */
    List<FilterExpression<T>> containsAny(final Iterable<V> values);

    /**
     * Generates an expression to select all elements with attributes matching all the given values.
     * For example: filtering by ["red", "blue"] would select only those elements with both "red" and "blue" values.
     * @param values the values to filter by
     * @return a filter expression for the given values
     */
    List<FilterExpression<T>> containsAll(final Iterable<V> values);

    /**
     * Generates an expression to select all elements with attributes matching any of the given values.
     * For example: filtering by ["red", "blue"] would select only those elements with either "red" or "blue" value.
     * @param values the values to filter by
     * @return a filter expression for the given values
     */
    List<FilterExpression<T>> containsAnyAsString(final Iterable<String> values);

    /**
     * Generates an expression to select all elements with attributes matching all the given values.
     * For example: filtering by ["red", "blue"] would select only those elements with both "red" and "blue" values.
     * @param values the values to filter by
     * @return a filter expression for the given values
     */
    List<FilterExpression<T>> containsAllAsString(final Iterable<String> values);

    /**
     * @deprecated use {@link #is(Object)} instead
     */
    @Deprecated
    default List<FilterExpression<T>> by(final V value) {
        return is(value);
    }


    /**
     * @deprecated use {@link #containsAny(Iterable)} instead
     */
    @Deprecated
    default List<FilterExpression<T>> byAny(final Iterable<V> values) {
        return containsAny(values);
    }

    /**
     * @deprecated use {@link #containsAll(Iterable)} instead
     */
    @Deprecated
    default List<FilterExpression<T>> byAll(final Iterable<V> values) {
        return containsAll(values);
    }

    /**
     * @deprecated use {@link #containsAnyAsString(Iterable)} instead
     */
    @Deprecated
    default List<FilterExpression<T>> byAnyAsString(final Iterable<String> values) {
        return containsAnyAsString(values);
    }

    /**
     * @deprecated use {@link #containsAllAsString(Iterable)} instead
     */
    @Deprecated
    default List<FilterExpression<T>> byAllAsString(final Iterable<String> values) {
        return containsAllAsString(values);
    }

}
