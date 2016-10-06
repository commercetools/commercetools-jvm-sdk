package io.sphere.sdk.search.model;

import io.sphere.sdk.search.*;

import java.util.function.Function;

/**
 * Model to build range and term facets.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
public final class RangeTermFacetSearchModel<T, V extends Comparable<? super V>> extends RangeTermFacetBaseSearchModel<T, V> {

    RangeTermFacetSearchModel(final SearchModel<T> searchModel, final Function<V, String> typeSerializer, final String alias, final Boolean isCountingProducts) {
        super(searchModel, typeSerializer, alias, isCountingProducts);
    }

    RangeTermFacetSearchModel(final SearchModel<T> searchModel, final Function<V, String> typeSerializer) {
        super(searchModel, typeSerializer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeTermFacetSearchModel<T, V> withAlias(final String alias) {
        return new RangeTermFacetSearchModel<>(searchModel, typeSerializer, alias, isCountingProducts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeTermFacetSearchModel<T, V> withCountingProducts(final Boolean isCountingProducts) {
        return new RangeTermFacetSearchModel<>(searchModel, typeSerializer, alias, isCountingProducts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermFacetExpression<T> allTerms() {
        return super.allTerms();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFacetExpression<T> allRanges() {
        return super.allRanges();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FilteredFacetExpression<T> onlyTerm(final V value) {
        return super.onlyTerm(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FilteredFacetExpression<T> onlyTerm(final Iterable<V> values) {
        return super.onlyTerm(values);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFacetExpression<T> onlyRange(final FacetRange<V> range) {
        return super.onlyRange(range);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFacetExpression<T> onlyRange(final Iterable<FacetRange<V>> facetRanges) {
        return super.onlyRange(facetRanges);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFacetExpression<T> onlyRange(final V lowerEndpoint, final V upperEndpoint) {
        return super.onlyRange(lowerEndpoint, upperEndpoint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFacetExpression<T> onlyGreaterThanOrEqualTo(final V value) {
        return super.onlyGreaterThanOrEqualTo(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFacetExpression<T> onlyLessThan(final V value) {
        return super.onlyLessThan(value);
    }

    /**
     * Creates an instance of the search model to generate range and term facet expressions.
     * @param attributePath the path of the attribute as expected by Commercetools Platform (e.g. "variants.attributes.color.key")
     * @param typeSerializer the function to convert the provided value to a string accepted by Commercetools Platform
     * @param <T> type of the resource
     * @param <V> type of the value
     * @return new instance of RangeTermFacetSearchModel
     */
    public static <T, V extends Comparable<? super V>> RangeTermFacetSearchModel<T, V> of(final String attributePath, final Function<V, String> typeSerializer) {
        return new RangeTermFacetSearchModel<>(new SearchModelImpl<>(attributePath), typeSerializer);
    }
}
