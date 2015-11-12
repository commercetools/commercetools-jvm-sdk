package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import javax.annotation.Nullable;

import java.util.List;
import java.util.function.Function;;

/**
 * Model to build range and term filters.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
public class RangeFilterSearchModel<T, V extends Comparable<? super V>> extends RangeFilterBaseSearchModel<T, V> {

    RangeFilterSearchModel(@Nullable final SearchModel<T> parent, final Function<V, String> typeSerializer) {
        super(parent, typeSerializer);
    }

    @Override
    public List<FilterExpression<T>> by(final V value) {
        return super.by(value);
    }

    @Override
    public List<FilterExpression<T>> byAny(final Iterable<V> values) {
        return super.byAny(values);
    }

    @Override
    public List<FilterExpression<T>> byAll(final Iterable<V> values) {
        return super.byAll(values);
    }

    @Override
    public List<FilterExpression<T>> byRange(final FilterRange<V> range) {
        return super.byRange(range);
    }

    @Override
    public List<FilterExpression<T>> byAnyRange(final Iterable<FilterRange<V>> filterRanges) {
        return super.byAnyRange(filterRanges);
    }

    @Override
    public List<FilterExpression<T>> byAllRanges(final Iterable<FilterRange<V>> filterRanges) {
        return super.byAllRanges(filterRanges);
    }

    @Override
    public List<FilterExpression<T>> byRange(final V lowerEndpoint, final V upperEndpoint) {
        return super.byRange(lowerEndpoint, upperEndpoint);
    }

    @Override
    public List<FilterExpression<T>> byGreaterThanOrEqualTo(final V value) {
        return super.byGreaterThanOrEqualTo(value);
    }

    @Override
    public List<FilterExpression<T>> byLessThanOrEqualTo(final V value) {
        return super.byLessThanOrEqualTo(value);
    }

    public static <T, V extends Comparable<? super V>> RangeFilterSearchModel<T, V> of(final String path, final Function<V, String> typeSerializer) {
        return new RangeFilterSearchModel<>(new SearchModelImpl<>(null, path), typeSerializer);
    }
}
