package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import java.util.List;
import java.util.function.Function;

/**
 * Model to build range and term filters.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
public final class RangeTermFilterSearchModel<T, V extends Comparable<? super V>> extends RangeTermFilterBaseSearchModel<T, V> {

    RangeTermFilterSearchModel(final SearchModel<T> searchModel, final Function<V, String> typeSerializer) {
        super(searchModel, typeSerializer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FilterExpression<T>> is(final V value) {
        return super.is(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FilterExpression<T>> isIn(final Iterable<V> values) {
        return super.isIn(values);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FilterExpression<T>> containsAny(final Iterable<V> values) {
        return super.containsAny(values);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FilterExpression<T>> containsAll(final Iterable<V> values) {
        return super.containsAll(values);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FilterExpression<T>> isBetween(final FilterRange<V> range) {
        return super.isBetween(range);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FilterExpression<T>> isBetween(final V lowerEndpoint, final V upperEndpoint) {
        return super.isBetween(lowerEndpoint, upperEndpoint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FilterExpression<T>> isBetweenAny(final Iterable<FilterRange<V>> filterRanges) {
        return super.isBetweenAny(filterRanges);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FilterExpression<T>> isBetweenAll(final Iterable<FilterRange<V>> filterRanges) {
        return super.isBetweenAll(filterRanges);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FilterExpression<T>> isGreaterThanOrEqualTo(final V value) {
        return super.isGreaterThanOrEqualTo(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FilterExpression<T>> isLessThanOrEqualTo(final V value) {
        return super.isLessThanOrEqualTo(value);
    }

    /**
     * Creates an instance of the search model to generate range and term filter expressions.
     * @param attributePath the path of the attribute as expected by Commercetools Platform (e.g. "variants.attributes.color.key")
     * @param typeSerializer the function to convert the provided value to a string accepted by Commercetools Platform
     * @param <T> type of the resource
     * @param <V> type of the value
     * @return new instance of RangeTermFilterSearchModel
     */
    public static <T, V extends Comparable<? super V>> RangeTermFilterSearchModel<T, V> of(final String attributePath,
                                                                                           final Function<V, String> typeSerializer) {
        return new RangeTermFilterSearchModel<>(new SearchModelImpl<>(attributePath), typeSerializer);
    }
}
