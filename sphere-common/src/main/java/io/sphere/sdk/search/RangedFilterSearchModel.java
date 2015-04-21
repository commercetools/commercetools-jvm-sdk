package io.sphere.sdk.search;

import java.util.Optional;

import static java.util.Arrays.asList;

public class RangedFilterSearchModel<T, V extends Comparable<? super V>> extends FilterSearchModel<T, V> {

    RangedFilterSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment, final TypeSerializer<V> typeSerializer) {
        super(parent, pathSegment, typeSerializer);
    }

    @Override
    public FilterExpression<T> exactly(final V value) {
        return super.exactly(value);
    }

    @Override
    public FilterExpression<T> exactly(final Iterable<V> values) {
        return super.exactly(values);
    }

    public FilterExpression<T> range(final FilterRange<V> range) {
        return range(asList(range));
    }

    public FilterExpression<T> range(final Iterable<FilterRange<V>> ranges) {
        return new RangeFilterExpression<>(this, typeSerializer, ranges);
    }

    public FilterExpression<T> range(final V lowerEndpoint, final V upperEndpoint) {
        return range(FilterRange.of(lowerEndpoint, upperEndpoint));
    }

    public FilterExpression<T> greaterThanOrEqualTo(final V value) {
        return range(FilterRange.atLeast(value));
    }

    public FilterExpression<T> lessThanOrEqualTo(final V value) {
        return range(FilterRange.atMost(value));
    }

    // NOT SUPPORTED YET
/*
    public FilterExpression<T> greaterThan(final V value) {
        return range(Range.greaterThan(value));
    }

    public FilterExpression<T> lessThan(final V value) {
        return range(Range.lessThan(value));
    }
*/
}
