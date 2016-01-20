package io.sphere.sdk.search.model;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.search.*;

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

    @Override
    @Nullable
    public String getAlias() {
        return alias;
    }

    @Override
    public TermFacetExpression<T> allTerms() {
        return new TermFacetExpressionImpl<>(searchModel, typeSerializer, alias);
    }

    @Override
    public FilteredFacetExpression<T> onlyTerm(final V value) {
        return onlyTerm(singletonList(value));
    }

    @Override
    public FilteredFacetExpression<T> onlyTerm(final Iterable<V> values) {
        return new FilteredFacetExpressionImpl<>(searchModel, typeSerializer, values, alias);
    }

    @Override
    public FilteredFacetExpression<T> onlyTermAsString(final Iterable<String> values) {
        return new FilteredFacetExpressionImpl<>(searchModel, TypeSerializer.ofString(), values, alias);
    }

    /**
     * Generates an expression to obtain the facets of the attribute for all values.
     * For example: a possible faceted classification could be ["red": 4, "yellow": 2, "blue": 1].
     * @return a facet expression for all values
     * @deprecated use {@link #allTerms()} instead
     */
    @Deprecated
    public TermFacetExpression<T> byAllTerms() {
        return new TermFacetExpressionImpl<>(searchModel, typeSerializer, alias);
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only the given value.
     * For example: a possible faceted classification for "red" could be ["red": 4].
     * @param value the value from which to obtain the facet
     * @return a facet expression for only the given value
     * @deprecated use {@link #onlyTerm(Object)}
     */
    @Deprecated
    public FilteredFacetExpression<T> byTerm(final V value) {
        return onlyTerm(singletonList(value));
    }

    /**
     * Generates an expression to obtain the facets of the attribute for only the given values.
     * For example: a possible faceted classification for ["red", "blue"] could be ["red": 4, "blue": 1].
     * @param values the values from which to obtain the facets
     * @return a facet expression for only the given values
     * @deprecated use {@link #onlyTerm(Iterable)}
     */
    @Deprecated
    public FilteredFacetExpression<T> byTerm(final Iterable<V> values) {
        return new FilteredFacetExpressionImpl<>(searchModel, typeSerializer, values, alias);
    }
}
