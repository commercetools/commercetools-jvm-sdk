package io.sphere.sdk.search;

import javax.annotation.Nullable;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

public class RangedFilterSearchModel<T, V extends Comparable<? super V>> extends FilterSearchModel<T, V> {

    RangedFilterSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment, final TypeSerializer<V> typeSerializer) {
        super(parent, pathSegment, typeSerializer);
    }

    @Override
    public FilterExpression<T> by(final V value) {
        return super.by(value);
    }

    @Override
    public FilterExpression<T> byAny(final Iterable<V> values) {
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
    public FilterExpression<T> byRange(final FilterRange<V> range) {
        return byAnyRange(singletonList(range));
    }

    /**
     * Generates an expression to select all elements with an attribute value within any of the given ranges.
     * For example: filtering by [[3, 7], [5, 9]] would select only those elements with values within 3 and 7, or within 5 and 9, both inclusive.
     * @param ranges the ranges of values to filter by
     * @return a filter expression for the given ranges
     */
    public FilterExpression<T> byAnyRange(final Iterable<FilterRange<V>> ranges) {
        return new RangeFilterExpression<>(this, typeSerializer, ranges);
    }

    /**
     * Generates an expression to select all elements with an attribute value within all the given ranges.
     * For example: filtering by [[3, 7], [5, 9]] would select only those elements with values within the range intersection [5, 7], inclusive.
     * @param ranges the ranges of values to filter by
     * @return a filter expression for the given ranges
     */
    public List<FilterExpression<T>> byAllRanges(final Iterable<FilterRange<V>> ranges) {
        return StreamSupport.stream(ranges.spliterator(), false)
                .map(range -> byRange(range))
                .collect(toList());
    }

    /**
     * Generates an expression to select all elements with an attribute value within the range defined by the given endpoints.
     * For example: filtering by [3, 5] would select only those elements with values between 3 and 5, inclusive.
     * @param lowerEndpoint the lower endpoint of the range of values to filter by, inclusive
     * @param upperEndpoint the upper endpoint of the range of values to filter by, inclusive
     * @return a filter expression for the given range
     */
    public FilterExpression<T> byRange(final V lowerEndpoint, final V upperEndpoint) {
        return byRange(FilterRange.of(lowerEndpoint, upperEndpoint));
    }

    /**
     * Generates an expression to select all elements with an attribute value greater than or equal to the given value.
     * For example: filtering by [3, +∞) would select only those elements with values greater than or equal to 3.
     * @param value the lower endpoint of the range [v, +∞)
     * @return a filter expression for the given range
     */
    public FilterExpression<T> byGreaterThanOrEqualTo(final V value) {
        return byRange(FilterRange.atLeast(value));
    }

    /**
     * Generates an expression to select all elements with an attribute value less than or equal to the given value.
     * For example: filtering by (-∞, 5] would select only those elements with values less than or equal to 5.
     * @param value the upper endpoint of the range (-∞, v]
     * @return a filter expression for the given range
     */
    public FilterExpression<T> byLessThanOrEqualTo(final V value) {
        return byRange(FilterRange.atMost(value));
    }

    // NOT SUPPORTED YET
/*
    public FilterExpression<T> byGreaterThan(final V value) {
        return range(Range.byGreaterThan(value));
    }

    public FilterExpression<T> byLessThan(final V value) {
        return range(Range.byLessThan(value));
    }
*/
}
