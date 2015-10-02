package io.sphere.sdk.search;

import javax.annotation.Nullable;
import java.util.List;

import static io.sphere.sdk.search.FacetRange.atLeast;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

public class FacetedSearchModel<T> extends SearchModelImpl<T> {
    @Nullable
    protected final String alias;
    protected final TypeSerializer<String> typeSerializer = TypeSerializer.ofString();

    public FacetedSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final String alias) {
        super(parent, pathSegment);
        this.alias = alias;
    }

    public FacetedSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
        this.alias = null;
    }

    /**
     * Allows to set an alias to identify the facet.
     * @param alias the identifier to use for the facet
     * @return a new faceted search model identical to the current one, but with the given alias
     */
    public FacetedSearchModel<T> withAlias(final String alias) {
        return new FacetedSearchModel<>(this, null, alias);
    }

    /**
     * Generates a term facet expression for the attribute, with no filter expression associated.
     * @return a faceted search expression
     */

    public FacetedSearchExpression<T> allTerms() {
        return termFacetedSearchOf(emptyList());
    }

    /**
     * Generates a range facet expression for the attribute, with no filter expression associated.
     * @return a faceted search expression
     */
    public FacetedSearchExpression<T> allRanges() {
        return rangeFacetedSearchOf(emptyList());
    }

    /**
     * Generates a term facet expression for the attribute and a filter expression to select all elements with the given attribute value.
     * @param value the value to filter by
     * @return a faceted search expression for the given value
     * @see FilterSearchModel#by(Object)
     */
    public FacetedSearchExpression<T> by(final String value) {
        return termFacetedSearchOf(filterByTerm(value));
    }

    /**
     * Generates a term facet expression for the attribute and a filter expression to select all elements with attributes matching any of the given values.
     * @param values the values to filter by
     * @return a faceted search expression for the given values
     * @see FilterSearchModel#byAny(Iterable)
     */
    public FacetedSearchExpression<T> byAny(final Iterable<String> values) {
        return termFacetedSearchOf(filterByTerm(values));
    }

    /**
     * Generates a term facet expression for the attribute and a filter expression to select all elements with attributes matching all the given values.
     * @param values the values to filter by
     * @return a faceted search expression for the given values
     * @see FilterSearchModel#byAll(Iterable)
     */
    public FacetedSearchExpression<T> byAll(final Iterable<String> values) {
        final List<FilterExpression<T>> filterExpressions = stream(values.spliterator(), false)
                .map(value -> filterByTerm(value))
                .collect(toList());
        return termFacetedSearchOf(filterExpressions);
    }

    /**
     * Generates a range facet expression for the attribute and a filter expression to select all elements with an attribute value within the given range.
     * @param range the range of values to filter by
     * @return a faceted search expression for the given range
     * @see RangedFilterSearchModel#byRange(FilterRange)
     */
    public FacetedSearchExpression<T> byRange(final FilterRange<String> range) {
        return rangeFacetedSearchOf(filterByRange(range));
    }

    /**
     * Generates a range facet expression for the attribute and a filter expression to select all elements with an attribute value within any of the given ranges.
     * @param ranges the ranges of values to filter by
     * @return a faceted search expression for the given ranges
     * @see RangedFilterSearchModel#byAllRanges(Iterable)
     */
    public FacetedSearchExpression<T> byAnyRange(final Iterable<FilterRange<String>> ranges) {
        return rangeFacetedSearchOf(filterByRange(ranges));
    }

    /**
     * Generates a range facet expression for the attribute and a filter expression to select all elements with an attribute value within all the given ranges.
     * @param ranges the ranges of values to filter by
     * @return a faceted search expression for the given ranges
     * @see RangedFilterSearchModel#byAllRanges(Iterable)
     */
    public FacetedSearchExpression<T> byAllRanges(final Iterable<FilterRange<String>> ranges) {
        final List<FilterExpression<T>> filterExpressions = stream(ranges.spliterator(), false)
                .map(range -> filterByRange(range))
                .collect(toList());
        return rangeFacetedSearchOf(filterExpressions);
    }

    /**
     * Generates a range facet expression for the attribute and a filter expression to select all elements with an attribute value within the range defined by the given endpoints.
     * @param lowerEndpoint the lower endpoint of the range of values to filter by, inclusive
     * @param upperEndpoint the upper endpoint of the range of values to filter by, inclusive
     * @return a faceted search expression for the given range
     * @see RangedFilterSearchModel#byRange(Comparable, Comparable)
     */
    public FacetedSearchExpression<T> byRange(final String lowerEndpoint, final String upperEndpoint) {
        final FilterRange<String> range = FilterRange.of(lowerEndpoint, upperEndpoint);
        return rangeFacetedSearchOf(filterByRange(range));
    }

    private FilterExpression<T> filterByTerm(final String value) {
        return filterByTerm(singletonList(value));
    }

    private FilterExpression<T> filterByTerm(final Iterable<String> values) {
        return new TermFilterExpression<>(this, typeSerializer, values);
    }

    private FilterExpression<T> filterByRange(final FilterRange<String> range) {
        return filterByRange(singletonList(range));
    }

    private FilterExpression<T> filterByRange(final Iterable<FilterRange<String>> ranges) {
        return new RangeFilterExpression<>(this, typeSerializer, ranges);
    }

    private FacetedSearchExpression<T> termFacetedSearchOf(final FilterExpression<T> filterExpression) {
        return termFacetedSearchOf(singletonList(filterExpression));
    }

    private FacetedSearchExpression<T> termFacetedSearchOf(final List<FilterExpression<T>> filterExpressions) {
        final FacetExpression<T> facetExpression = new TermFacetExpression<>(this, typeSerializer, alias);
        return new FacetedSearchExpressionImpl<>(facetExpression, filterExpressions);
    }

    private FacetedSearchExpression<T> rangeFacetedSearchOf(final FilterExpression<T> filterExpression) {
        return rangeFacetedSearchOf(singletonList(filterExpression));
    }

    private FacetedSearchExpression<T> rangeFacetedSearchOf(final List<FilterExpression<T>> filterExpressions) {
        // Unable to request (* to *), from 0 to * will at least cover all positive numbers - typical usage.
        // If negative values are in the scope, then please use facet/filter expressions instead.
        final List<FacetRange<String>> ranges = singletonList(atLeast("0"));
        final FacetExpression<T> facetExpression = new RangeFacetExpression<>(this, typeSerializer, ranges, alias);
        return new FacetedSearchExpressionImpl<>(facetExpression, filterExpressions);
    }
}
