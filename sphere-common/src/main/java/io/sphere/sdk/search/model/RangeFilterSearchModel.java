package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import javax.annotation.Nullable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public class RangeFilterSearchModel<T, V extends Comparable<? super V>> extends FilterSearchModel<T, V> {

    RangeFilterSearchModel(@Nullable final SearchModel<T> parent, final Function<V, String> typeSerializer) {
        super(parent, typeSerializer);
    }

    @Override
    public List<FilterExpression<T>> by(final V value) {
        return super.by(value);
    }

    @Override
    public List<FilterExpression<T>> byAny(final Iterable<V> values) {
        return super.byAny(values);
    }

    @Override
    public List<FilterExpression<T>> byAll(final Iterable<V> values) {
        return super.byAll(values);
    }

    /**
     * Generates an expression to select all elements with an attribute value within the given range.
     * For example: filtering by [3, 7] would select only those elements with values between 3 and 7, inclusive.
     * @param range the range of values to filter by
     * @return a filter expression for the given range
     */
    public List<FilterExpression<T>> byRange(final FilterRange<V> range) {
        return singletonList(filterBy(range));
    }

    /**
     * Generates an expression to select all elements with an attribute value within any of the given ranges.
     * For example: filtering by [[3, 7], [5, 9]] would select only those elements with values within 3 and 7, or within 5 and 9, both inclusive.
     * @param ranges the ranges of values to filter by
     * @return a filter expression for the given ranges
     */
    public List<FilterExpression<T>> byAnyRange(final Iterable<FilterRange<V>> ranges) {
        return singletonList(filterBy(ranges));
    }

    /**
     * Generates an expression to select all elements with an attribute value within all the given ranges.
     * For example: filtering by [[3, 7], [5, 9]] would select only those elements with values within the range intersection [5, 7], inclusive.
     * @param ranges the ranges of values to filter by
     * @return a filter expression for the given ranges
     */
    public List<FilterExpression<T>> byAllRanges(final Iterable<FilterRange<V>> ranges) {
        return StreamSupport.stream(ranges.spliterator(), false)
                .map(range -> filterBy(range))
                .collect(toList());
    }

    /**
     * Generates an expression to select all elements with an attribute value within the range defined by the given endpoints.
     * For example: filtering by [3, 5] would select only those elements with values between 3 and 5, inclusive.
     * @param lowerEndpoint the lower endpoint of the range of values to filter by, inclusive
     * @param upperEndpoint the upper endpoint of the range of values to filter by, inclusive
     * @return a filter expression for the given range
     */
    public List<FilterExpression<T>> byRange(final V lowerEndpoint, final V upperEndpoint) {
        return singletonList(filterBy(FilterRange.of(lowerEndpoint, upperEndpoint)));
    }

    /**
     * Generates an expression to select all elements with an attribute value greater than or equal to the given value.
     * For example: filtering by [3, +∞) would select only those elements with values greater than or equal to 3.
     * @param value the lower endpoint of the range [v, +∞)
     * @return a filter expression for the given range
     */
    public List<FilterExpression<T>> byGreaterThanOrEqualTo(final V value) {
        return singletonList(filterBy(FilterRange.atLeast(value)));
    }

    /**
     * Generates an expression to select all elements with an attribute value less than or equal to the given value.
     * For example: filtering by (-∞, 5] would select only those elements with values less than or equal to 5.
     * @param value the upper endpoint of the range (-∞, v]
     * @return a filter expression for the given range
     */
    public List<FilterExpression<T>> byLessThanOrEqualTo(final V value) {
        return singletonList(filterBy(FilterRange.atMost(value)));
    }

    // NOT SUPPORTED YET


//    public List<FilterExpression<T>> byGreaterThan(final V value) {
//        return singletonList(filterBy(Range.byGreaterThan(value)));
//    }
//
//    public List<FilterExpression<T>> byLessThan(final V value) {
//        return singletonList(filterBy(Range.byLessThan(value)));
//    }


    private RangeFilterExpression<T, V> filterBy(final FilterRange<V> range) {
        return filterBy(singletonList(range));
    }

    private RangeFilterExpression<T, V> filterBy(final Iterable<FilterRange<V>> ranges) {
        return new RangeFilterExpression<>(this, typeSerializer, ranges);
    }
}
