package io.sphere.sdk.search;

import javax.annotation.Nullable;
import java.util.Optional;

import static java.util.Arrays.asList;

public class RangedFacetSearchModel<T, V extends Comparable<? super V>> extends FacetSearchModel<T, V> {

    RangedFacetSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment, final TypeSerializer<V> typeSerializer, final String alias) {
        super(parent, pathSegment, typeSerializer, alias);
    }

    RangedFacetSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment, final TypeSerializer<V> typeSerializer) {
        super(parent, pathSegment, typeSerializer, null);
    }

    @Override
    public RangedFacetSearchModel<T, V> withAlias(final String alias) {
        return new RangedFacetSearchModel<>(this, null, typeSerializer, alias);
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

    public RangeFacetExpression<T, V> range(final FacetRange<V> range) {
        return range(asList(range));
    }

    public RangeFacetExpression<T, V> range(final Iterable<FacetRange<V>> ranges) {
        return new RangeFacetExpression<>(this, typeSerializer, ranges, alias);
    }

    public RangeFacetExpression<T, V> range(final V lowerEndpoint, final V upperEndpoint) {
        return range(FacetRange.of(lowerEndpoint, upperEndpoint));
    }

    public RangeFacetExpression<T, V> greaterThanOrEqualTo(final V value) {
        return range(FacetRange.atLeast(value));
    }

    public RangeFacetExpression<T, V> lessThan(final V value) {
        return range(FacetRange.lessThan(value));
    }

    // NOT SUPPORTED YET
/*
    public RangeFacetExpression<T, V> greaterThan(final V value) {
        return range(Range.greaterThan(value));
    }

    public RangeFacetExpression<T, V> lessThanOrEqualTo(final V value) {
        return range(Range.atMost(value));
    }

    public RangeFacetExpression<T, V> allRanges() {
        return range(Range.all());
    }
*/
}
