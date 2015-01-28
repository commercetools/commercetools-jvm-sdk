package io.sphere.sdk.search;

import java.util.Optional;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class RangeTermFacetSearchModel<T, V extends Comparable<? super V>> extends TermFacetSearchModel<T, V> {
    private static final String BOUNDLESS = "*";

    RangeTermFacetSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment, final TypeSerializer<V> typeSerializer) {
        super(parent, pathSegment, typeSerializer);
    }

    @Override
    public FacetExpression<T> allTerms() {
        return super.allTerms();
    }

    @Override
    public FacetExpression<T> only(final V value) {
        return super.only(value);
    }

    @Override
    public FacetExpression<T> only(final Iterable<V> values) {
        return super.only(values);
    }

    public FacetExpression<T> onlyWithin(final Range<V> range) {
        return onlyWithin(asList(range));
    }

    public FacetExpression<T> onlyWithin(final Iterable<Range<V>> ranges) {
        return new RangeFacetExpression<>(this, toStringRanges(ranges));
    }

    public FacetExpression<T> onlyGreaterThan(final V value) {
        return onlyWithin(Range.greaterThan(value));
    }

    public FacetExpression<T> onlyLessThan(final V value) {
        return onlyWithin(Range.lessThan(value));
    }

    // NOT IMPLEMENTED YET
/*
    public FacetExpression<T> onlyGreaterThanOrEqualsTo(final V value) {
        return onlyWithin(Range.atLeast(value));
    }

    public FacetExpression<T> onlyLessThanOrEqualsTo(final V value) {
        return onlyWithin(Range.atMost(value));
    }
*/

    public FacetExpression<T> allRanges() {
        return onlyWithin(Range.all());
    }

    private Iterable<String> toStringRanges(Iterable<Range<V>> ranges) {
        return toStream(ranges).map(r -> toStringRange(r)).collect(toList());
    }

    private String toStringRange(final Range<V> range) {
        return String.format("(%s to %s)",
                range.lowerEndpoint().map(e -> typeSerializer.serializer().apply(e)).orElse(BOUNDLESS),
                range.upperEndpoint().map(e -> typeSerializer.serializer().apply(e)).orElse(BOUNDLESS));
    }
}
