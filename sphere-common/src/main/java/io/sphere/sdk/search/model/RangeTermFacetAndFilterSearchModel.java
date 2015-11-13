package io.sphere.sdk.search.model;

import io.sphere.sdk.search.RangeFacetAndFilterExpression;
import io.sphere.sdk.search.TermFacetAndFilterExpression;

/**
 * Model to build range and term facets and filters.
 * @param <T> type of the resource
 */
public class RangeTermFacetAndFilterSearchModel<T> extends RangeTermFacetAndFilterBaseSearchModel<T> {

    RangeTermFacetAndFilterSearchModel(final SearchModel<T> searchModel) {
        super(searchModel);
    }

    @Override
    public RangeFacetAndFilterExpression<T> byRange(final FilterRange<String> range) {
        return super.byRange(range);
    }

    @Override
    public RangeFacetAndFilterExpression<T> byAnyRange(final Iterable<FilterRange<String>> ranges) {
        return super.byAnyRange(ranges);
    }

    @Override
    public RangeFacetAndFilterExpression<T> byAllRanges(final Iterable<FilterRange<String>> ranges) {
        return super.byAllRanges(ranges);
    }

    @Override
    public RangeFacetAndFilterExpression<T> allRanges() {
        return super.allRanges();
    }

    @Override
    public TermFacetAndFilterExpression<T> allTerms() {
        return super.allTerms();
    }

    @Override
    public TermFacetAndFilterExpression<T> by(final String value) {
        return super.by(value);
    }

    @Override
    public TermFacetAndFilterExpression<T> byAny(final Iterable<String> values) {
        return super.byAny(values);
    }

    @Override
    public TermFacetAndFilterExpression<T> byAll(final Iterable<String> values) {
        return super.byAll(values);
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
}
