package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilteredFacetExpression;
import io.sphere.sdk.search.TermFacetExpression;

import java.util.function.Function;

/**
 * Model to build term facets.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
public class TermFacetSearchModel<T, V> extends TermFacetBaseSearchModel<T, V> {

    TermFacetSearchModel(final SearchModel<T> searchModel, final Function<V, String> typeSerializer, final String alias) {
        super(searchModel, typeSerializer, alias);
    }

    TermFacetSearchModel(final SearchModel<T> searchModel, final Function<V, String> typeSerializer) {
        super(searchModel, typeSerializer);
    }

    @Override
    public TermFacetSearchModel<T, V> withAlias(final String alias) {
        return new TermFacetSearchModel<>(searchModel, typeSerializer, alias);
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

    public static <T, V> TermFacetSearchModel<T, V> of(final String path, final Function<V, String> typeSerializer) {
        return new TermFacetSearchModel<>(new SearchModelImpl<>(path), typeSerializer);
    }
}
