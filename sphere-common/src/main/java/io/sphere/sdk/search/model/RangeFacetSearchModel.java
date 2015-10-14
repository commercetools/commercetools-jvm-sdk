package io.sphere.sdk.search.model;

import io.sphere.sdk.search.*;

import javax.annotation.Nullable;

import java.util.function.Function;

import static java.util.Collections.singletonList;

public class RangeFacetSearchModel<T, V extends Comparable<? super V>> extends FacetSearchModel<T, V> {

    RangeFacetSearchModel(@Nullable final SearchModel<T> parent, final Function<V, String> typeSerializer, final String alias) {
        super(parent, typeSerializer, alias);
    }

    RangeFacetSearchModel(@Nullable final SearchModel<T> parent, final Function<V, String> typeSerializer) {
        super(parent, typeSerializer);
    }

    @Override
    public RangeFacetSearchModel<T, V> withAlias(final String alias) {
        return new RangeFacetSearchModel<>(this, typeSerializer, alias);
    }

    @Override
    public TermFacetExpression<T> allTerms() {
        return super.allTerms();
    }

    @Override
    public FilteredFacetExpression<T> onlyTerm(final V value) {
        return super.onlyTerm(value);
    }

    @Override
    public FilteredFacetExpression<T> onlyTerm(final Iterable<V> values) {
        return super.onlyTerm(values);
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
     * For example: a possible faceted classification for (-∞, 5] could be ["3": 4, "1": 3, "5": 2, "4": 1].
     * @param value the upper endpoint of the range (-∞, v]
     * @return a facet expression for only the given range
     */
    public RangeFacetExpression<T> onlyLessThan(final V value) {
        return onlyRange(FacetRange.lessThan(value));
    }

    // NOT SUPPORTED YET
/*
    public RangeFacetExpression<T> onlyGreaterThan(final V value) {
        return range(Range.greaterThan(value));
    }

    public RangeFacetExpression<T> onlyLessThanOrEqualTo(final V value) {
        return range(Range.atMost(value));
    }

    public RangeFacetExpression<T> allRanges() {
        return range(Range.all());
    }
*/
}
