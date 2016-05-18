package io.sphere.sdk.search.model;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.search.FilteredFacetExpression;
import io.sphere.sdk.search.TermFacetExpression;

import javax.annotation.Nullable;
import java.util.function.Function;

import static java.util.Collections.singletonList;

/**
 * Model to build term facets.
 * This class is abstract to force the subclass to select the methods that need to be highlighted and/or extended.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
abstract class TermFacetBaseSearchModel<T, V> extends Base implements FacetSearchModel<T, V> {
    protected final SearchModel<T> searchModel;
    protected final Function<V, String> typeSerializer;
    @Nullable
    protected final String alias;

    TermFacetBaseSearchModel(final SearchModel<T> searchModel, final Function<V, String> typeSerializer, @Nullable final String alias) {
        this.searchModel = searchModel;
        this.typeSerializer = typeSerializer;
        this.alias = alias;
    }

    TermFacetBaseSearchModel(final SearchModel<T> searchModel, final Function<V, String> typeSerializer) {
        this(searchModel, typeSerializer, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    public String getAlias() {
        return alias;
    }

    @Override
    public SearchModel<T> getSearchModel() {
        return searchModel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TermFacetExpression<T> allTerms() {
        return new TermFacetExpressionImpl<>(searchModel, typeSerializer, alias);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FilteredFacetExpression<T> onlyTerm(final V value) {
        return onlyTerm(singletonList(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FilteredFacetExpression<T> onlyTerm(final Iterable<V> values) {
        return new FilteredFacetExpressionImpl<>(searchModel, typeSerializer, values, alias);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FilteredFacetExpression<T> onlyTermAsString(final Iterable<String> values) {
        return new FilteredFacetExpressionImpl<>(searchModel, TypeSerializer.ofString(), values, alias);
    }
}
