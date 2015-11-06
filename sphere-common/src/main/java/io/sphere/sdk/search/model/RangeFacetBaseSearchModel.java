package io.sphere.sdk.search.model;

import io.sphere.sdk.search.RangeFacetExpression;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

abstract class RangeFacetBaseSearchModel<T, V extends Comparable<? super V>> extends FacetSearchModel<T, V> {

    RangeFacetBaseSearchModel(@Nullable final SearchModel<T> parent, final Function<V, String> typeSerializer, final String alias) {
        super(parent, typeSerializer, alias);
    }

    RangeFacetBaseSearchModel(@Nullable final SearchModel<T> parent, final Function<V, String> typeSerializer) {
        super(parent, typeSerializer);
    }

    /**
     * Generates an expression to obtain the facets of the attribute for all values.
     * For example: a possible faceted classification could be ["red": 4, "yellow": 2, "blue": 1].
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
        return new RangeFacetExpressionImpl<>(this, typeSerializer, ranges, alias);
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
        return new RangeFacetExpressionImpl<>(this, TypeSerializer.ofString(), ranges, alias);
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only the given range.
     * For example: a possible faceted classification for [3, 5] could be ["3": 4, "5": 2, "4": 1].
     * @param lowerEndpoint the lower endpoint of the range of values to be present in the facet, inclusive
     * @param upperEndpoint the upper endpoint of the range of values to be present in the facet, inclusive
     * @return a facet expression for only the given range
     * @deprecated use {@link #onlyRangeAsString(FacetRange)}
     */
    @Deprecated
    public RangeFacetExpression<T> onlyRangeAsString(final String lowerEndpoint, final String upperEndpoint) {
        final List<FacetRange<String>> ranges = singletonList(FacetRange.of(lowerEndpoint, upperEndpoint));
        return new RangeFacetExpressionImpl<>(this, TypeSerializer.ofString(), ranges, alias);
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only the given range.
     * For example: a possible faceted classification for [3, 5] could be ["3": 4, "5": 2, "4": 1].
     * @param range the range of values to be present in the facet
     * @return a facet expression for only the given range
     * @deprecated use {@link #onlyRange(FacetRange)}
     */
    @Deprecated
    public RangeFacetExpression<T> byRange(final FacetRange<V> range) {
        return onlyRange(singletonList(range));
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only the given ranges.
     * For example: a possible faceted classification for [[3, 5], [8, 9]] could be ["3": 4, "9": 3, "5": 2, "4": 1].
     * @param ranges the ranges of values to be present in the facet
     * @return a facet expression for only the given ranges
     * @deprecated use {@link #onlyRange(Iterable)}
     */
    @Deprecated
    public RangeFacetExpression<T> byRange(final Iterable<FacetRange<V>> ranges) {
        return new RangeFacetExpressionImpl<>(this, typeSerializer, ranges, alias);
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only the given range.
     * For example: a possible faceted classification for [3, 5] could be ["3": 4, "5": 2, "4": 1].
     * @param lowerEndpoint the lower endpoint of the range of values to be present in the facet, inclusive
     * @param upperEndpoint the upper endpoint of the range of values to be present in the facet, inclusive
     * @return a facet expression for only the given range
     * @deprecated use {@link #onlyRange(Comparable, Comparable)}
     */
    @Deprecated
    public RangeFacetExpression<T> byRange(final V lowerEndpoint, final V upperEndpoint) {
        return onlyRange(FacetRange.of(lowerEndpoint, upperEndpoint));
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only values greater than or equal to the given value.
     * For example: a possible faceted classification for [3, +∞) could be ["15": 6, "3": 4, "9": 3, "5": 2, "4": 1].
     * @param value the lower endpoint of the range [v, +∞)
     * @return a facet expression for only the given range
     * @deprecated use {@link #onlyGreaterThanOrEqualTo(Comparable)}
     */
    @Deprecated
    public RangeFacetExpression<T> byGreaterThanOrEqualTo(final V value) {
        return onlyRange(FacetRange.atLeast(value));
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only values less than the given value.
     * For example: a possible faceted classification for (-∞, 5] could be ["3": 4, "1": 3, "5": 2, "4": 1].
     * @param value the upper endpoint of the range (-∞, v]
     * @return a facet expression for only the given range
     * @deprecated use {@link #onlyLessThan(Comparable)}
     */
    @Deprecated
    public RangeFacetExpression<T> byLessThan(final V value) {
        return onlyRange(FacetRange.lessThan(value));
    }
}
