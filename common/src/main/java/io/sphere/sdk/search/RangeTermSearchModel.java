package io.sphere.sdk.search;

import java.util.Optional;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public abstract class RangeTermSearchModel<T, V extends Comparable<? super V>> extends TermSearchModel<T, V> {
    private static final String BOUNDLESS = "*";

    public RangeTermSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public RangeTermSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public FacetExpression<T> isWithin(final Range<V> range) {
        return isWithin(asList(range));
    }

    public FacetExpression<T> isWithin(final Iterable<Range<V>> ranges) {
        return new RangeFacetExpression<>(this, toStringRanges(ranges));
    }

    public FacetExpression<T> isGreaterThan(final V value) {
        return isWithin(Range.greaterThan(value));
    }

    public FacetExpression<T> isLessThan(final V value) {
        return isWithin(Range.lessThan(value));
    }

    // NOT IMPLEMENTED YET
/*
    public FacetExpression<T> isGreaterThanOrEqualsTo(final V value) {
        return isWithin(Range.atLeast(value));
    }

    public FacetExpression<T> isLessThanOrEqualsTo(final V value) {
        return isWithin(Range.atMost(value));
    }
*/
    public FacetExpression<T> anyRange() {
        return isWithin(Range.all());
    }

    private Iterable<String> toStringRanges(Iterable<Range<V>> ranges) {
        return toStream(ranges).map(r -> toStringRange(r)).collect(toList());
    }

    private String toStringRange(final Range<V> range) {
        return String.format("(%s to %s)",
                range.lowerEndpoint().map(e -> render(e)).orElse(BOUNDLESS),
                range.upperEndpoint().map(e -> render(e)).orElse(BOUNDLESS));
    }
}
