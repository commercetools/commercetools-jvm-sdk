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
     * The search model for the filter.
     * @return the filter search model
     */
    SearchModel<T> getSearchModel();

    /**
     * Generates an expression to select all elements with the given attribute value.
     * For example: filtering by "red" would select only those elements with "red" value.
     * @param value the value to filter by
     * @return a filter expression for the given value
     */
    List<FilterExpression<T>> is(final V value);

    /**
     * Generates an expression to select all elements with attributes matching any of the given values.
     * For example: filtering by ["red", "blue"] would select only those elements with either "red" or "blue" value.
     *
     * <p>Alias for {@link #containsAny(Iterable)}.</p>
     *
     * @param values the values to filter by
     * @return a filter expression for the given values
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
}
