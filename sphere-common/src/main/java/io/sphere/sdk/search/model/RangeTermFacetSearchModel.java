package io.sphere.sdk.search.model;

import io.sphere.sdk.search.*;

import java.util.function.Function;

/**
 * Model to build range and term facets.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
public class RangeTermFacetSearchModel<T, V extends Comparable<? super V>> extends RangeTermFacetBaseSearchModel<T, V> {

    RangeTermFacetSearchModel(final SearchModel<T> searchModel, final Function<V, String> typeSerializer, final String alias) {
        super(searchModel, typeSerializer, alias);
    }

    RangeTermFacetSearchModel(final SearchModel<T> searchModel, final Function<V, String> typeSerializer) {
        super(searchModel, typeSerializer);
    }

    @Override
    public RangeTermFacetSearchModel<T, V> withAlias(final String alias) {
        return new RangeTermFacetSearchModel<>(searchModel, typeSerializer, alias);
    }

    @Override
    public TermFacetExpression<T> allTerms() {
        return super.allTerms();
    }

    @Override
    public RangeFacetExpression<T> allRanges() {
        return super.allRanges();
    }

    @Override
    public FilteredFacetExpression<T> onlyTerm(final V value) {
        return super.onlyTerm(value);
    }

    @Override
    public FilteredFacetExpression<T> onlyTerm(final Iterable<V> values) {
        return super.onlyTerm(values);
    }

    @Override
    public RangeFacetExpression<T> onlyRange(final FacetRange<V> range) {
        return super.onlyRange(range);
    }

    @Override
    public RangeFacetExpression<T> onlyRange(final Iterable<FacetRange<V>> facetRanges) {
        return super.onlyRange(facetRanges);
    }

    @Override
    public RangeFacetExpression<T> onlyRange(final V lowerEndpoint, final V upperEndpoint) {
        return super.onlyRange(lowerEndpoint, upperEndpoint);
    }

    @Override
    public RangeFacetExpression<T> onlyGreaterThanOrEqualTo(final V value) {
        return super.onlyGreaterThanOrEqualTo(value);
    }

    @Override
    public RangeFacetExpression<T> onlyLessThan(final V value) {
        return super.onlyLessThan(value);
    }

    public static <T, V extends Comparable<? super V>> RangeTermFacetSearchModel<T, V> of(final String path, final Function<V, String> typeSerializer) {
        return new RangeTermFacetSearchModel<>(new SearchModelImpl<>(path), typeSerializer);
    }
}
