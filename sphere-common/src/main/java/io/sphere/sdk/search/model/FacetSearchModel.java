package io.sphere.sdk.search.model;

import io.sphere.sdk.search.*;

import javax.annotation.Nullable;

import java.util.function.Function;

import static java.util.Collections.singletonList;

abstract class FacetSearchModel<T, V> extends SearchModelImpl<T> {
    @Nullable
    protected final String alias;
    protected final Function<V, String> typeSerializer;

    FacetSearchModel(@Nullable final SearchModel<T> parent, final Function<V, String> typeSerializer, final String alias) {
        super(parent, null);
        this.typeSerializer = typeSerializer;
        this.alias = alias;
    }

    FacetSearchModel(@Nullable final SearchModel<T> parent, final Function<V, String> typeSerializer) {
        super(parent, null);
        this.typeSerializer = typeSerializer;
        this.alias = null;
    }

    @Nullable
    public String getAlias() {
        return alias;
    }

    /**
     * Allows to set an alias to identify the facet.
     * @param alias the identifier to use for the facet
     * @return a new facet search model identical to the current one, but with the given alias
     */
    public abstract FacetSearchModel<T, V> withAlias(final String alias);

    /**
     * Generates an expression to obtain the facets of the attribute for all values.
     * For example: a possible faceted classification could be ["red": 4, "yellow": 2, "blue": 1].
     * @return a facet expression for all values
     */
    public TermFacetExpression<T> allTerms() {
        return new TermFacetExpressionImpl<>(this, typeSerializer, alias);
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only the given value.
     * For example: a possible faceted classification for "red" could be ["red": 4].
     * @param value the value from which to obtain the facet
     * @return a facet expression for only the given value
     */
    public FilteredFacetExpression<T> onlyTerm(final V value) {
        return onlyTerm(singletonList(value));
    }

    /**
     * Generates an expression to obtain the facets of the attribute for only the given values.
     * For example: a possible faceted classification for ["red", "blue"] could be ["red": 4, "blue": 1].
     * @param values the values from which to obtain the facets
     * @return a facet expression for only the given values
     */
    public FilteredFacetExpression<T> onlyTerm(final Iterable<V> values) {
        return new FilteredFacetExpressionImpl<>(this, typeSerializer, values, alias);
    }
}
