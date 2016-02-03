package io.sphere.sdk.search.model;

import io.sphere.sdk.search.RangeFacetedSearchExpression;
import io.sphere.sdk.search.TermFacetedSearchExpression;

/**
 * Model to build range and term facets and filters.
 * @param <T> type of the resource
 */
public final class RangeTermFacetedSearchSearchModel<T> extends RangeTermFacetedSearchBaseSearchModel<T> {

    RangeTermFacetedSearchSearchModel(final SearchModel<T> searchModel) {
        super(searchModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFacetedSearchExpression<T> isBetween(final FilterRange<String> range) {
        return super.isBetween(range);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFacetedSearchExpression<T> isBetweenAny(final Iterable<FilterRange<String>> ranges) {
        return super.isBetweenAny(ranges);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFacetedSearchExpression<T> isBetweenAll(final Iterable<FilterRange<String>> ranges) {
        return super.isBetweenAll(ranges);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFacetedSearchExpression<T> allRanges() {
        return super.allRanges();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermFacetedSearchExpression<T> allTerms() {
        return super.allTerms();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermFacetedSearchExpression<T> is(final String value) {
        return super.is(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermFacetedSearchExpression<T> isIn(final Iterable<String> values) {
        return super.isIn(values);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermFacetedSearchExpression<T> containsAny(final Iterable<String> values) {
        return super.containsAny(values);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermFacetedSearchExpression<T> containsAll(final Iterable<String> values) {
        return super.containsAll(values);
    }

    /**
     * Creates an instance of the search model to generate range and term faceted search expressions.
     * @param attributePath the path of the attribute as expected by Commercetools Platform (e.g. "variants.attributes.color.key")
     * @param <T> type of the resource
     * @return new instance of RangeTermFacetAndFilterSearchModel
     */
    public static <T> RangeTermFacetedSearchSearchModel<T> of(final String attributePath) {
        return new RangeTermFacetedSearchSearchModel<>(new SearchModelImpl<>(attributePath));
    }
}
