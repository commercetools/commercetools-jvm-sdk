package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import java.util.List;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

/**
 * Model to build range and term filters.
 * This class is abstract to force the subclass to select the methods that need to be highlighted and/or extended.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
abstract class RangeTermFilterBaseSearchModel<T, V extends Comparable<? super V>> extends TermFilterBaseSearchModel<T, V> {

    RangeTermFilterBaseSearchModel(final SearchModel<T> searchModel, final Function<V, String> typeSerializer) {
        super(searchModel, typeSerializer);
    }

    /**
     * Generates an expression to select all elements with an attribute value within the given range.
     * For example: filtering by [3, 7] would select only those elements with values between 3 and 7, inclusive.
     * @param range the range of values to filter by
     * @return a filter expression for the given range
     */
    public List<FilterExpression<T>> isBetween(final FilterRange<V> range) {
        return singletonList(filterBy(range));
    }

    /**
     * Generates an expression to select all elements with an attribute value within the range defined by the given endpoints.
     * For example: filtering by [3, 5] would select only those elements with values between 3 and 5, inclusive.
     * @param lowerEndpoint the lower endpoint of the range of values to filter by, inclusive
     * @param upperEndpoint the upper endpoint of the range of values to filter by, inclusive
     * @return a filter expression for the given range
     */
    public List<FilterExpression<T>> isBetween(final V lowerEndpoint, final V upperEndpoint) {
        return singletonList(filterBy(FilterRange.of(lowerEndpoint, upperEndpoint)));
    }

    /**
     * Generates an expression to select all elements with an attribute value within any of the given ranges.
     * For example: filtering by [[3, 7], [5, 9]] would select only those elements with values within 3 and 7, or within 5 and 9, both inclusive.
     * @param ranges the ranges of values to filter by
     * @return a filter expression for the given ranges
     */
    public List<FilterExpression<T>> isBetweenAny(final Iterable<FilterRange<V>> ranges) {
        return singletonList(filterBy(ranges));
    }

    /**
     * Generates an expression to select all elements with an attribute value within all the given ranges.
     * For example: filtering by [[3, 7], [5, 9]] would select only those elements with values within the range intersection [5, 7], inclusive.
     * @param ranges the ranges of values to filter by
     * @return a filter expression for the given ranges
     */
    public List<FilterExpression<T>> isBetweenAll(final Iterable<FilterRange<V>> ranges) {
        return StreamSupport.stream(ranges.spliterator(), false)
                .map(range -> filterBy(range))
                .collect(toList());
    }

    /**
     * Generates an expression to select all elements with an attribute value greater than or equal to the given value.
     * For example: filtering by [3, +∞) would select only those elements with values greater than or equal to 3.
     * @param value the lower endpoint of the range [v, +∞)
     * @return a filter expression for the given range
     */
    public List<FilterExpression<T>> isGreaterThanOrEqualTo(final V value) {
        return singletonList(filterBy(FilterRange.atLeast(value)));
    }

    /**
     * Generates an expression to select all elements with an attribute value less than or equal to the given value.
     * For example: filtering by (-∞, 5] would select only those elements with values less than or equal to 5.
     * @param value the upper endpoint of the range (-∞, v]
     * @return a filter expression for the given range
     */
    public List<FilterExpression<T>> isLessThanOrEqualTo(final V value) {
        return singletonList(filterBy(FilterRange.atMost(value)));
    }

    /**
     * Generates an expression to select all elements with an attribute value within the given range.
     * For example: filtering by [3, 7] would select only those elements with values between 3 and 7, inclusive.
     * @param range the range of values (as string) to filter by
     * @return a filter expression for the given range
     */
    public List<FilterExpression<T>> isBetweenAsString(final FilterRange<String> range) {
        return singletonList(filterByAsString(range));
    }

    /**
     * Generates an expression to select all elements with an attribute value within any of the given ranges.
     * For example: filtering by [[3, 7], [5, 9]] would select only those elements with values within 3 and 7, or within 5 and 9, both inclusive.
     * @param ranges the ranges of values (as string) to filter by
     * @return a filter expression for the given ranges
     */
    public List<FilterExpression<T>> isBetweenAnyAsString(final Iterable<FilterRange<String>> ranges) {
        return singletonList(filterByAsString(ranges));
    }

    /**
     * Generates an expression to select all elements with an attribute value within all the given ranges.
     * For example: filtering by [[3, 7], [5, 9]] would select only those elements with values within the range intersection [5, 7], inclusive.
     * @param ranges the ranges of values (as string) to filter by
     * @return a filter expression for the given ranges
     */
    public List<FilterExpression<T>> isBetweenAllAsString(final Iterable<FilterRange<String>> ranges) {
        return StreamSupport.stream(ranges.spliterator(), false)
                .map(range -> filterByAsString(range))
                .collect(toList());
    }

    private RangeFilterExpression<T, V> filterBy(final FilterRange<V> range) {
        return filterBy(singletonList(range));
    }

    private RangeFilterExpression<T, V> filterBy(final Iterable<FilterRange<V>> ranges) {
        return new RangeFilterExpression<>(searchModel, typeSerializer, ranges);
    }

    private RangeFilterExpression<T, String> filterByAsString(final FilterRange<String> range) {
        return filterByAsString(singletonList(range));
    }

    private RangeFilterExpression<T, String> filterByAsString(final Iterable<FilterRange<String>> ranges) {
        return new RangeFilterExpression<>(searchModel, TypeSerializer.ofString(), ranges);
    }
}
