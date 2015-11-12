package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilteredFacetExpression;
import io.sphere.sdk.search.TermFacetExpression;

import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * Model to build term facets.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
public class TermFacetSearchModel<T, V> extends TermFacetBaseSearchModel<T, V> {

    TermFacetSearchModel(@Nullable final SearchModel<T> parent, final Function<V, String> typeSerializer, final String alias) {
        super(parent, typeSerializer, alias);
    }

    TermFacetSearchModel(@Nullable final SearchModel<T> parent, final Function<V, String> typeSerializer) {
        super(parent, typeSerializer);
    }

    @Override
    public TermFacetSearchModel<T, V> withAlias(final String alias) {
        return new TermFacetSearchModel<>(this, typeSerializer, alias);
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
        return new TermFacetSearchModel<>(new SearchModelImpl<>(null, path), typeSerializer);
    }
}
