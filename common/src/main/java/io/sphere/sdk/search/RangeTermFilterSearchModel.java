package io.sphere.sdk.search;

import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class RangeTermFilterSearchModel<T, V extends Comparable<? super V>> extends TermFilterSearchModel<T, V> {
    private static final String BOUNDLESS = "*";

    RangeTermFilterSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment, final Function<V, String> renderer) {
        super(parent, pathSegment, renderer);
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
        return new RangeFilterExpression<>(this, toStringRanges(ranges));
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

    private Iterable<String> toStringRanges(Iterable<Range<V>> ranges) {
        return toStream(ranges).map(r -> toStringRange(r)).collect(toList());
    }

    private String toStringRange(final Range<V> range) {
        return String.format("(%s to %s)",
                range.lowerEndpoint().map(e -> renderer.apply(e)).orElse(BOUNDLESS),
                range.upperEndpoint().map(e -> renderer.apply(e)).orElse(BOUNDLESS));
    }
}
