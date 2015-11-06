package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.RangeFacetAndFilterExpression;
import io.sphere.sdk.search.RangeFacetExpression;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

abstract class RangeFacetAndFilterBaseSearchModel<T, V extends Comparable<? super V>> extends FacetAndFilterSearchModel<T, V> {
    private final RangeFacetExpression<T> facetExpression;
    private final RangeFilterSearchModel<T, V> filterSearchModel;

    RangeFacetAndFilterBaseSearchModel(@Nullable final SearchModel<T> parent, final Function<V, String> typeSerializer) {
        super(parent, typeSerializer);
        this.facetExpression = new RangeFacetSearchModel<>(parent, typeSerializer).allRanges();
        this.filterSearchModel = new RangeFilterSearchModel<>(parent, typeSerializer);
    }

    /**
     * Generates an expression to select all elements with an attribute value within the given range.
     * For example: filtering by [3, 7] would select only those elements with values between 3 and 7, inclusive.
     * @param range the range of values to filter by
     * @return a filter expression for the given range
     */
    public RangeFacetAndFilterExpression<T> byRange(final FilterRange<V> range) {
        return expression(filterSearchModel.byRange(range));
    }

    /**
     * Generates an expression to select all elements with an attribute value within any of the given ranges.
     * For example: filtering by [[3, 7], [5, 9]] would select only those elements with values within 3 and 7, or within 5 and 9, both inclusive.
     * @param ranges the ranges of values to filter by
     * @return a filter expression for the given ranges
     */
    public RangeFacetAndFilterExpression<T> byAnyRange(final Iterable<FilterRange<V>> ranges) {
        return expression(filterSearchModel.byAnyRange(ranges));
    }

    /**
     * Generates an expression to select all elements with an attribute value within all the given ranges.
     * For example: filtering by [[3, 7], [5, 9]] would select only those elements with values within the range intersection [5, 7], inclusive.
     * @param ranges the ranges of values to filter by
     * @return a filter expression for the given ranges
     */
    public RangeFacetAndFilterExpression<T> byAllRanges(final Iterable<FilterRange<V>> ranges) {
        return expression(filterSearchModel.byAllRanges(ranges));
    }

    /**
     * Generates an expression to select all elements with an attribute value within the range defined by the given endpoints.
     * For example: filtering by [3, 5] would select only those elements with values between 3 and 5, inclusive.
     * @param lowerEndpoint the lower endpoint of the range of values to filter by, inclusive
     * @param upperEndpoint the upper endpoint of the range of values to filter by, inclusive
     * @return a filter expression for the given range
     */
    public RangeFacetAndFilterExpression<T> byRange(final V lowerEndpoint, final V upperEndpoint) {
        return expression(filterSearchModel.byRange(lowerEndpoint, upperEndpoint));
    }

    /**
     * Generates an expression to select all elements with an attribute value greater than or equal to the given value.
     * For example: filtering by [3, +∞) would select only those elements with values greater than or equal to 3.
     * @param value the lower endpoint of the range [v, +∞)
     * @return a filter expression for the given range
     */
    public RangeFacetAndFilterExpression<T> byGreaterThanOrEqualTo(final V value) {
        return expression(filterSearchModel.byGreaterThanOrEqualTo(value));
    }

    /**
     * Generates an expression to select all elements with an attribute value less than or equal to the given value.
     * For example: filtering by (-∞, 5] would select only those elements with values less than or equal to 5.
     * @param value the upper endpoint of the range (-∞, v]
     * @return a filter expression for the given range
     */
    public RangeFacetAndFilterExpression<T> byLessThanOrEqualTo(final V value) {
        return expression(filterSearchModel.byLessThanOrEqualTo(value));
    }

    /**
     * Generates an expression to select all elements with an attribute value within any of the given ranges.
     * For example: filtering by [[3, 7], [5, 9]] would select only those elements with values within 3 and 7, or within 5 and 9, both inclusive.
     * @param ranges the ranges of values to filter by
     * @return a filter expression for the given ranges
     */
    public RangeFacetAndFilterExpression<T> byAnyRangeAsString(final Iterable<FilterRange<String>> ranges) {
        return expression(filterSearchModel.byAnyRangeAsString(ranges));
    }

    /**
     * Generates an expression to select all elements with an attribute value within all the given ranges.
     * For example: filtering by [[3, 7], [5, 9]] would select only those elements with values within the range intersection [5, 7], inclusive.
     * @param ranges the ranges of values to filter by
     * @return a filter expression for the given ranges
     */
    public RangeFacetAndFilterExpression<T> byAllRangesAsString(final Iterable<FilterRange<String>> ranges) {
        return expression(filterSearchModel.byAllRangesAsString(ranges));
    }

    private RangeFacetAndFilterExpressionImpl<T> expression(final List<FilterExpression<T>> filterExpressions) {
        return new RangeFacetAndFilterExpressionImpl<>(facetExpression, filterExpressions);
    }
}
