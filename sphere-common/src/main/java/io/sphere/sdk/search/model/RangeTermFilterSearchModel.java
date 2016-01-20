package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import java.util.List;
import java.util.function.Function;

/**
 * Model to build range and term filters.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
public class RangeTermFilterSearchModel<T, V extends Comparable<? super V>> extends RangeTermFilterBaseSearchModel<T, V> {

    RangeTermFilterSearchModel(final SearchModel<T> searchModel, final Function<V, String> typeSerializer) {
        super(searchModel, typeSerializer);
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

    /**
     * Creates an instance of the search model to generate range and term filter expressions.
     * @param attributePath the path of the attribute as expected by Commercetools Platform (e.g. "variants.attributes.color.key")
     * @param typeSerializer the function to convert the provided value to a string accepted by Commercetools Platform
     * @param <T> type of the resource
     * @param <V> type of the value
     * @return new instance of RangeTermFilterSearchModel
     */
    public static <T, V extends Comparable<? super V>> RangeTermFilterSearchModel<T, V> of(final String attributePath, final Function<V, String> typeSerializer) {
        return new RangeTermFilterSearchModel<>(new SearchModelImpl<>(attributePath), typeSerializer);
    }
}
