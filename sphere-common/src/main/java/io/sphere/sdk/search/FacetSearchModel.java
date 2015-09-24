package io.sphere.sdk.search;

import javax.annotation.Nullable;

import static java.util.Collections.singletonList;

public class FacetSearchModel<T, V> extends SearchModelImpl<T> {
    @Nullable
    protected final String alias;
    protected final TypeSerializer<V> typeSerializer;

    FacetSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment, final TypeSerializer<V> typeSerializer, final String alias) {
        super(parent, pathSegment);
        this.alias = alias;
        this.typeSerializer = typeSerializer;
    }

    FacetSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment, final TypeSerializer<V> typeSerializer) {
        super(parent, pathSegment);
        this.alias = null;
        this.typeSerializer = typeSerializer;
    }

    /**
     * Allows to set an alias to identify the facet.
     * @param alias the identifier to use for the facet
     * @return a new facet search model identical to the current one, but with the given alias
     */
    public FacetSearchModel<T, V> withAlias(final String alias) {
        return new FacetSearchModel<>(this, null, typeSerializer, alias);
    }

    /**
     * Generates an expression to obtain the facets of the attribute for all values.
     * For example: a possible faceted classification could be ["red": 4, "yellow": 2, "blue": 1].
     * @return a facet expression for all values
     */
    public TermFacetExpression<T, V> byAllTerms() {
        return new TermFacetExpression<>(this, typeSerializer, alias);
    }

    /**
     * Generates an expression to obtain the facet of the attribute for only the given value.
     * For example: a possible faceted classification for "red" could be ["red": 4].
     * @param value the value from which to obtain the facet
     * @return a facet expression for only the given value
     */
    public FilteredFacetExpression<T, V> byTerm(final V value) {
        return byTerm(singletonList(value));
    }

    /**
     * Generates an expression to obtain the facets of the attribute for only the given values.
     * For example: a possible faceted classification for ["red", "blue"] could be ["red": 4, "blue": 1].
     * @param values the values from which to obtain the facets
     * @return a facet expression for only the given values
     */
    public FilteredFacetExpression<T, V> byTerm(final Iterable<V> values) {
        return new FilteredFacetExpression<>(this, typeSerializer, values, alias);
    }
}
