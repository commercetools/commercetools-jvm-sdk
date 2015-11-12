package io.sphere.sdk.search.model;

import io.sphere.sdk.search.RangeFacetAndFilterExpression;
import io.sphere.sdk.search.TermFacetAndFilterExpression;

import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * Model to build range and term facets and filters.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
public class RangeFacetAndFilterSearchModel<T, V extends Comparable<? super V>> extends RangeFacetAndFilterBaseSearchModel<T, V> {

    RangeFacetAndFilterSearchModel(@Nullable final SearchModel<T> parent, final Function<V, String> typeSerializer) {
        super(parent, typeSerializer);
    }

    @Override
    public TermFacetAndFilterExpression<T> by(final V value) {
        return super.by(value);
    }

    @Override
    public TermFacetAndFilterExpression<T> byAny(final Iterable<V> values) {
        return super.byAny(values);
    }

    @Override
    public TermFacetAndFilterExpression<T> byAll(final Iterable<V> values) {
        return super.byAll(values);
    }

    @Override
    public RangeFacetAndFilterExpression<T> byRange(final FilterRange<V> range) {
        return super.byRange(range);
    }

    @Override
    public RangeFacetAndFilterExpression<T> byAnyRange(final Iterable<FilterRange<V>> filterRanges) {
        return super.byAnyRange(filterRanges);
    }

    @Override
    public RangeFacetAndFilterExpression<T> byAllRanges(final Iterable<FilterRange<V>> filterRanges) {
        return super.byAllRanges(filterRanges);
    }

    @Override
    public RangeFacetAndFilterExpression<T> byRange(final V lowerEndpoint, final V upperEndpoint) {
        return super.byRange(lowerEndpoint, upperEndpoint);
    }

    @Override
    public RangeFacetAndFilterExpression<T> byGreaterThanOrEqualTo(final V value) {
        return super.byGreaterThanOrEqualTo(value);
    }

    @Override
    public RangeFacetAndFilterExpression<T> byLessThanOrEqualTo(final V value) {
        return super.byLessThanOrEqualTo(value);
    }

    public static <T, V extends Comparable<? super V>> RangeFacetAndFilterSearchModel<T, V> of(final String path, final Function<V, String> typeSerializer) {
        return new RangeFacetAndFilterSearchModel<>(new SearchModelImpl<>(null, path), typeSerializer);
    }

}
