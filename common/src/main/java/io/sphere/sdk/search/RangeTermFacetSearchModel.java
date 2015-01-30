package io.sphere.sdk.search;

import java.util.Optional;

import static java.util.Arrays.asList;

public class RangeTermFacetSearchModel<T, V extends Comparable<? super V>> extends TermFacetSearchModel<T, V> {

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

    public FacetExpression<T> onlyWithin(final FacetRange<V> range) {
        return onlyWithin(asList(range));
    }

    public FacetExpression<T> onlyWithin(final Iterable<FacetRange<V>> ranges) {
        return new RangeFacetExpression<>(this, ranges, typeSerializer);
    }

    public FacetExpression<T> onlyWithin(final V lowerEndpoint, final V upperEndpoint) {
        return onlyWithin(FacetRange.of(lowerEndpoint, upperEndpoint));
    }

    public FacetExpression<T> onlyGreaterThanOrEqualTo(final V value) {
        return onlyWithin(FacetRange.atLeast(value));
    }

    public FacetExpression<T> onlyLessThan(final V value) {
        return onlyWithin(FacetRange.lessThan(value));
    }

    // NOT SUPPORTED YET
/*
    public FacetExpression<T> onlyGreaterThan(final V value) {
        return onlyWithin(Range.greaterThan(value));
    }

    public FacetExpression<T> onlyLessThanOrEqualTo(final V value) {
        return onlyWithin(Range.atMost(value));
    }

    public FacetExpression<T> allRanges() {
        return onlyWithin(Range.all());
    }
*/
}
