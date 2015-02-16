package io.sphere.sdk.search;

import java.util.Optional;

import static java.util.Arrays.asList;

public class RangedFacetSearchModel<T, V extends Comparable<? super V>> extends FacetSearchModel<T, V> {

    RangedFacetSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment, final TypeSerializer<V> typeSerializer, final Optional<String> alias) {
        super(parent, pathSegment, typeSerializer, alias);
    }

    RangedFacetSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment, final TypeSerializer<V> typeSerializer) {
        super(parent, pathSegment, typeSerializer, Optional.empty());
    }

    @Override
    public RangedFacetSearchModel<T, V> withAlias(final Optional<String> alias) {
        return new RangedFacetSearchModel<>(Optional.of(this), Optional.empty(), typeSerializer, alias);
    }

    @Override
    public RangedFacetSearchModel<T, V> withAlias(final String alias) {
        return withAlias(Optional.of(alias));
    }

    @Override
    public TermFacetExpression<T, V> all() {
        return super.all();
    }

    @Override
    public FilteredFacetExpression<T, V> only(final V value) {
        return super.only(value);
    }

    @Override
    public FilteredFacetExpression<T, V> only(final Iterable<V> values) {
        return super.only(values);
    }

    public RangeFacetExpression<T, V> onlyWithin(final FacetRange<V> range) {
        return onlyWithin(asList(range));
    }

    public RangeFacetExpression<T, V> onlyWithin(final Iterable<FacetRange<V>> ranges) {
        return new RangeFacetExpression<>(this, typeSerializer, ranges, alias);
    }

    public RangeFacetExpression<T, V> onlyWithin(final V lowerEndpoint, final V upperEndpoint) {
        return onlyWithin(FacetRange.of(lowerEndpoint, upperEndpoint));
    }

    public RangeFacetExpression<T, V> onlyGreaterThanOrEqualTo(final V value) {
        return onlyWithin(FacetRange.atLeast(value));
    }

    public RangeFacetExpression<T, V> onlyLessThan(final V value) {
        return onlyWithin(FacetRange.lessThan(value));
    }

    // NOT SUPPORTED YET
/*
    public RangeFacetExpression<T, V> onlyGreaterThan(final V value) {
        return onlyWithin(Range.greaterThan(value));
    }

    public RangeFacetExpression<T, V> onlyLessThanOrEqualTo(final V value) {
        return onlyWithin(Range.atMost(value));
    }

    public RangeFacetExpression<T, V> allRanges() {
        return onlyWithin(Range.all());
    }
*/
}
