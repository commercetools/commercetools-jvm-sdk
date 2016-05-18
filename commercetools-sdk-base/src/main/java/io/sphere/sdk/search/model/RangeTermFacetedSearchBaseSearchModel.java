package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.RangeFacetedSearchExpression;
import io.sphere.sdk.search.RangeFacetExpression;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Model to build range and term facets and filters.
 * This class is abstract to force the subclass to select the methods that need to be highlighted and/or extended.
 * @param <T> type of the resource
 */
abstract class RangeTermFacetedSearchBaseSearchModel<T> extends TermFacetedSearchBaseSearchModel<T> {
    private final RangeFacetExpression<T> facetExpression;
    private final RangeTermFilterSearchModel<T, String> filterSearchModel;

    RangeTermFacetedSearchBaseSearchModel(final SearchModel<T> searchModel) {
        super(searchModel);
        this.facetExpression = new RangeTermFacetSearchModel<>(searchModel, TYPE_SERIALIZER).allRanges();
        this.filterSearchModel = new RangeTermFilterSearchModel<>(searchModel, TYPE_SERIALIZER);
    }

    /**
     * Generates an expression to select all elements, without filtering, along with the facet for all terms for this attribute.
     * @return a bundle of an empty filter expression and a facet expression for all terms
     */
    public RangeFacetedSearchExpression<T> allRanges() {
        return buildExpression(emptyList());
    }

    /**
     * Generates an expression to select all elements with an attribute value within the given range, along with the facet for all ranges for this attribute.
     * For example: filtering by [3, 7] would select only those elements with values between 3 and 7, inclusive, while the facet would contain statistical information about the range.
     * @param range the range of values to filter by
     * @return a bundle of the filter expressions for the given range and a facet expression for all ranges
     */
    public RangeFacetedSearchExpression<T> isBetween(final FilterRange<String> range) {
        return isBetweenAny(singletonList(range));
    }

    /**
     * Generates an expression to select all elements with an attribute value within any of the given ranges, along with the facet for all ranges for this attribute.
     * For example: filtering by [[3, 7], [5, 9]] would select only those elements with values within 3 and 7, or within 5 and 9, both inclusive, while the color facet would contain statistical information about the ranges.
     * @param ranges the ranges of values to filter by
     * @return a bundle of the filter expressions for the given ranges and a facet expression for all ranges
     */
    public RangeFacetedSearchExpression<T> isBetweenAny(final Iterable<FilterRange<String>> ranges) {
        return buildExpression(filterSearchModel.isBetweenAnyAsString(ranges));
    }

    /**
     * Generates an expression to select all elements with an attribute value within all the given ranges, along with the facet for all ranges for this attribute.
     * For example: filtering by [[3, 7], [5, 9]] would select only those elements with values within the range intersection [5, 7], inclusive, while the color facet would contain statistical information about the ranges.
     * @param ranges the ranges of values to filter by
     * @return a bundle of the filter expressions for the given ranges and a facet expression for all ranges
     */
    public RangeFacetedSearchExpression<T> isBetweenAll(final Iterable<FilterRange<String>> ranges) {
        return buildExpression(filterSearchModel.isBetweenAllAsString(ranges));
    }

    private RangeFacetedSearchExpression<T> buildExpression(final List<FilterExpression<T>> filterExpressions) {
        return RangeFacetedSearchExpression.of(facetExpression, filterExpressions);
    }
}
