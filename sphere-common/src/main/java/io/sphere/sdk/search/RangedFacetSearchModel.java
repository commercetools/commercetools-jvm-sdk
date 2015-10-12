package io.sphere.sdk.search;

import javax.annotation.Nullable;

import static java.util.Collections.singletonList;

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
    public TermFacetExpression<T> byAllTerms() {
        return super.byAllTerms();
    }

    @Override
    public FilteredFacetExpression<T> byTerm(final V value) {
        return super.byTerm(value);
    }

    @Override
    public FilteredFacetExpression<T> byTerm(final Iterable<V> values) {
        return super.byTerm(values);
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only the given range.
     * For example: a possible faceted classification for [3, 5] could be ["3": 4, "5": 2, "4": 1].
     * @param range the range of values to be present in the facet
     * @return a facet expression for only the given range
     */
    public RangeFacetExpression<T> byRange(final FacetRange<V> range) {
        return byRange(singletonList(range));
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only the given ranges.
     * For example: a possible faceted classification for [[3, 5], [8, 9]] could be ["3": 4, "9": 3, "5": 2, "4": 1].
     * @param ranges the ranges of values to be present in the facet
     * @return a facet expression for only the given ranges
     */
    public RangeFacetExpression<T> byRange(final Iterable<FacetRange<V>> ranges) {
        return new RangeFacetExpressionImpl<>(this, typeSerializer, ranges, alias);
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only the given range.
     * For example: a possible faceted classification for [3, 5] could be ["3": 4, "5": 2, "4": 1].
     * @param lowerEndpoint the lower endpoint of the range of values to be present in the facet, inclusive
     * @param upperEndpoint the upper endpoint of the range of values to be present in the facet, inclusive
     * @return a facet expression for only the given range
     */
    public RangeFacetExpression<T> byRange(final V lowerEndpoint, final V upperEndpoint) {
        return byRange(FacetRange.of(lowerEndpoint, upperEndpoint));
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only values greater than or equal to the given value.
     * For example: a possible faceted classification for [3, +∞) could be ["15": 6, "3": 4, "9": 3, "5": 2, "4": 1].
     * @param value the lower endpoint of the range [v, +∞)
     * @return a facet expression for only the given range
     */
    public RangeFacetExpression<T> byGreaterThanOrEqualTo(final V value) {
        return byRange(FacetRange.atLeast(value));
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only values less than the given value.
     * For example: a possible faceted classification for (-∞, 5] could be ["3": 4, "1": 3, "5": 2, "4": 1].
     * @param value the upper endpoint of the range (-∞, v]
     * @return a facet expression for only the given range
     */
    public RangeFacetExpression<T> byLessThan(final V value) {
        return byRange(FacetRange.lessThan(value));
    }

    // NOT SUPPORTED YET
/*
    public RangeFacetExpression<T> byGreaterThan(final V value) {
        return range(Range.greaterThan(value));
    }

    public RangeFacetExpression<T> byLessThanOrEqualTo(final V value) {
        return range(Range.atMost(value));
    }

    public RangeFacetExpression<T> byAllRanges() {
        return range(Range.all());
    }
*/
}
