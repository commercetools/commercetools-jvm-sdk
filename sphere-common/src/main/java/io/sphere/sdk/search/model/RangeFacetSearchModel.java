package io.sphere.sdk.search.model;

import io.sphere.sdk.search.*;

import javax.annotation.Nullable;

import java.util.function.Function;

public class RangeFacetSearchModel<T, V extends Comparable<? super V>> extends RangeFacetBaseSearchModel<T, V> {

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
}
