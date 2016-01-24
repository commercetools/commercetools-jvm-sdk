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

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFacetAndFilterExpression<T> isBetween(final FilterRange<String> range) {
        return super.isBetween(range);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFacetAndFilterExpression<T> isBetweenAny(final Iterable<FilterRange<String>> ranges) {
        return super.isBetweenAny(ranges);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFacetAndFilterExpression<T> isBetweenAll(final Iterable<FilterRange<String>> ranges) {
        return super.isBetweenAll(ranges);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFacetAndFilterExpression<T> allRanges() {
        return super.allRanges();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermFacetAndFilterExpression<T> allTerms() {
        return super.allTerms();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermFacetAndFilterExpression<T> is(final String value) {
        return super.is(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermFacetAndFilterExpression<T> isIn(final Iterable<String> values) {
        return super.isIn(values);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermFacetAndFilterExpression<T> containsAny(final Iterable<String> values) {
        return super.containsAny(values);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermFacetAndFilterExpression<T> containsAll(final Iterable<String> values) {
        return super.containsAll(values);
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
