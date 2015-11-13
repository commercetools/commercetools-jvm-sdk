package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.RangeFacetAndFilterExpression;
import io.sphere.sdk.search.RangeFacetExpression;

import java.util.List;
import java.util.function.Function;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * Model to build range and term facets and filters.
 * @param <T> type of the resource
 */
public class RangeTermFacetAndFilterSearchModel<T> extends TermFacetAndFilterSearchModel<T> {
    private final RangeFacetExpression<T> facetExpression;
    private final RangeTermFilterSearchModel<T, String> filterSearchModel;

    RangeTermFacetAndFilterSearchModel(final SearchModel<T> searchModel) {
        super(searchModel);
        this.facetExpression = new RangeTermFacetSearchModel<>(searchModel, TYPE_SERIALIZER).allRanges();
        this.filterSearchModel = new RangeTermFilterSearchModel<>(searchModel, TYPE_SERIALIZER);
    }

    /**
     * Generates an expression to select all elements with an attribute value within the given range.
     * For example: filtering by [3, 7] would select only those elements with values between 3 and 7, inclusive.
     * @param range the range of values to filter by
     * @return a filter expression for the given range
     */
    public RangeFacetAndFilterExpression<T> byRange(final FilterRange<String> range) {
        return byAnyRange(singletonList(range));
    }

    /**
     * Generates an expression to select all elements with an attribute value within any of the given ranges.
     * For example: filtering by [[3, 7], [5, 9]] would select only those elements with values within 3 and 7, or within 5 and 9, both inclusive.
     * @param ranges the ranges of values to filter by
     * @return a filter expression for the given ranges
     */
    public RangeFacetAndFilterExpression<T> byAnyRange(final Iterable<FilterRange<String>> ranges) {
        return validateAndBuildExpression(ranges, r -> filterSearchModel.byAnyRangeAsString(r));
    }

    /**
     * Generates an expression to select all elements with an attribute value within all the given ranges.
     * For example: filtering by [[3, 7], [5, 9]] would select only those elements with values within the range intersection [5, 7], inclusive.
     * @param ranges the ranges of values to filter by
     * @return a filter expression for the given ranges
     */
    public RangeFacetAndFilterExpression<T> byAllRanges(final Iterable<FilterRange<String>> ranges) {
        return validateAndBuildExpression(ranges, r -> filterSearchModel.byAllRangesAsString(r));
    }

    /**
     * Creates an instance of the search model to generate range and term faceted search expressions.
     * @param attributePath the path of the attribute as expected by Commercetools Platform (e.g. "variants.attributes.color.key")
     * @param <T> type of the resource
     * @return new instance of RangeTermFacetAndFilterSearchModel
     */
    public static <T> RangeTermFacetAndFilterSearchModel<T> of(final String attributePath) {
        return new RangeTermFacetAndFilterSearchModel<>(new SearchModelImpl<>(attributePath));
    }

    private <R extends Comparable<? super R>> RangeFacetAndFilterExpression<T> validateAndBuildExpression(final Iterable<FilterRange<R>> ranges,
                                                                                                          final Function<Iterable<FilterRange<R>>, List<FilterExpression<T>>> f) {
        final List<FilterExpression<T>> filterExpressions;
        if (ranges.iterator().hasNext()) {
            filterExpressions = f.apply(ranges);
        } else {
            filterExpressions = emptyList();
        }
        return RangeFacetAndFilterExpression.of(facetExpression, filterExpressions);
    }
}
