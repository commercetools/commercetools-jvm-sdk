package io.sphere.sdk.search;

import java.util.Optional;

import static java.util.Arrays.asList;

public class RangeTermFilterSearchModel<T, V extends Comparable<? super V>> extends TermFilterSearchModel<T, V> {

    RangeTermFilterSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment, final TypeSerializer<V> typeSerializer) {
        super(parent, pathSegment, typeSerializer);
    }

    @Override
    public FilterExpression<T> is(final V value) {
        return super.is(value);
    }

    @Override
    public FilterExpression<T> isIn(final Iterable<V> values) {
        return super.isIn(values);
    }

    public FilterExpression<T> isWithin(final Range<V> range) {
        return isWithin(asList(range));
    }

    public FilterExpression<T> isWithin(final Iterable<Range<V>> ranges) {
        return new RangeFilterExpression<>(this, ranges, typeSerializer);
    }

    public FilterExpression<T> isGreaterThan(final V value) {
        return isWithin(Range.greaterThan(value));
    }

    public FilterExpression<T> isLessThan(final V value) {
        return isWithin(Range.lessThan(value));
    }

    // NOT IMPLEMENTED YET
/*
    public FilterExpression<T> isGreaterThanOrEqualsTo(final V value) {
        return isWithin(Range.atLeast(value));
    }

    public FilterExpression<T> isLessThanOrEqualsTo(final V value) {
        return isWithin(Range.atMost(value));
    }
*/
}
