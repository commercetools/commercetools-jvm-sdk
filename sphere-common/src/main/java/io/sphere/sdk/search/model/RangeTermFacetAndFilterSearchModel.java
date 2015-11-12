package io.sphere.sdk.search.model;

import io.sphere.sdk.search.RangeFacetAndFilterExpression;
import io.sphere.sdk.search.TermFacetAndFilterExpression;

import java.util.function.Function;

/**
 * Model to build range and term facets and filters.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
public class RangeTermFacetAndFilterSearchModel<T, V extends Comparable<? super V>> extends RangeTermFacetAndFilterBaseSearchModel<T, V> {

    RangeTermFacetAndFilterSearchModel(final SearchModel<T> searchModel, final Function<V, String> typeSerializer) {
        super(searchModel, typeSerializer);
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

    /**
     * Creates an instance of the search model to generate range and term faceted search expressions.
     * @param attributePath the path of the attribute as expected by Commercetools Platform (e.g. "variants.attributes.color.key")
     * @param typeSerializer the function to convert the provided value to a string accepted by Commercetools Platform
     * @param <T> type of the resource
     * @param <V> type of the value
     * @return new instance of RangeTermFacetAndFilterSearchModel
     */
    public static <T, V extends Comparable<? super V>> RangeTermFacetAndFilterSearchModel<T, V> of(final String attributePath, final Function<V, String> typeSerializer) {
        return new RangeTermFacetAndFilterSearchModel<>(new SearchModelImpl<>(attributePath), typeSerializer);
    }

}
