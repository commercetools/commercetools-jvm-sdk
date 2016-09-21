package io.sphere.sdk.search.model;

import io.sphere.sdk.search.RangeFacetExpression;

import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

/**
 * Model to build range and term facets.
 * This class is abstract to force the subclass to select the methods that need to be highlighted and/or extended.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
abstract class RangeTermFacetBaseSearchModel<T, V extends Comparable<? super V>> extends TermFacetBaseSearchModel<T, V> {

    RangeTermFacetBaseSearchModel(final SearchModel<T> searchModel, final Function<V, String> typeSerializer, final String alias, final Boolean isCountingProducts) {
        super(searchModel, typeSerializer, alias, isCountingProducts);
    }

    RangeTermFacetBaseSearchModel(final SearchModel<T> searchModel, final Function<V, String> typeSerializer) {
        super(searchModel, typeSerializer);
    }

    /**
     * Generates an expression to obtain the facet of the attribute for any range.
     * For example: a possible faceted classification could be ["-40": 4, "105": 2, "4": 1].
     * Notice that this method generates a facet expression with two ranges: (-∞, 0] and [0, +∞), therefore two results should be expected.
     * @return a facet expression for all values
     */
    public RangeFacetExpression<T> allRanges() {
        return onlyRangeAsString(asList(FacetRange.lessThan("0"), FacetRange.atLeast("0")));
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only the given range.
     * For example: a possible faceted classification for [3, 5] could be ["3": 4, "5": 2, "4": 1].
     * @param range the range of values to be present in the facet
     * @return a facet expression for only the given range
     */
    public RangeFacetExpression<T> onlyRange(final FacetRange<V> range) {
        return onlyRange(singletonList(range));
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only the given ranges.
     * For example: a possible faceted classification for [[3, 5], [8, 9]] could be ["3": 4, "9": 3, "5": 2, "4": 1].
     * @param ranges the ranges of values to be present in the facet
     * @return a facet expression for only the given ranges
     */
    public RangeFacetExpression<T> onlyRange(final Iterable<FacetRange<V>> ranges) {
        return new RangeFacetExpressionImpl<>(searchModel, typeSerializer, ranges, alias, isCountingProducts);
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only the given range.
     * For example: a possible faceted classification for [3, 5] could be ["3": 4, "5": 2, "4": 1].
     * @param lowerEndpoint the lower endpoint of the range of values to be present in the facet, inclusive
     * @param upperEndpoint the upper endpoint of the range of values to be present in the facet, inclusive
     * @return a facet expression for only the given range
     */
    public RangeFacetExpression<T> onlyRange(final V lowerEndpoint, final V upperEndpoint) {
        return onlyRange(FacetRange.of(lowerEndpoint, upperEndpoint));
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only values greater than or equal to the given value.
     * For example: a possible faceted classification for [3, +∞) could be ["15": 6, "3": 4, "9": 3, "5": 2, "4": 1].
     * @param value the lower endpoint of the range [v, +∞)
     * @return a facet expression for only the given range
     */
    public RangeFacetExpression<T> onlyGreaterThanOrEqualTo(final V value) {
        return onlyRange(FacetRange.atLeast(value));
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only values less than the given value.
     * For example: a possible faceted classification for (-∞, 5) could be ["3": 4, "1": 3, "4": 1].
     * @param value the upper endpoint of the range (-∞, v)
     * @return a facet expression for only the given range
     */
    public RangeFacetExpression<T> onlyLessThan(final V value) {
        return onlyRange(FacetRange.lessThan(value));
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only the given range.
     * For example: a possible faceted classification for [3, 5] could be ["3": 4, "5": 2, "4": 1].
     * @param range the range of values (as string) to be present in the facet
     * @return a facet expression for only the given range
     */
    public RangeFacetExpression<T> onlyRangeAsString(final FacetRange<String> range) {
        return onlyRangeAsString(singletonList(range));
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only the given ranges.
     * For example: a possible faceted classification for [[3, 5], [8, 9]] could be ["3": 4, "9": 3, "5": 2, "4": 1].
     * @param ranges the ranges of values (as string) to be present in the facet
     * @return a facet expression for only the given ranges
     */
    public RangeFacetExpression<T> onlyRangeAsString(final Iterable<FacetRange<String>> ranges) {
        return new RangeFacetExpressionImpl<>(searchModel, TypeSerializer.ofString(), ranges, alias, isCountingProducts);
    }
}
