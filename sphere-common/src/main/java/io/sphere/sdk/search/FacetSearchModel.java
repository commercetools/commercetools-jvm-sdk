package io.sphere.sdk.search;

import javax.annotation.Nullable;
import java.util.Optional;

import static java.util.Arrays.asList;

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

    public FacetSearchModel<T, V> withAlias(final String alias) {
        return new FacetSearchModel<>(this, null, typeSerializer, alias);
    }

    public TermFacetExpression<T, V> all() {
        return new TermFacetExpression<>(this, typeSerializer, alias);
    }

    public FilteredFacetExpression<T, V> only(final V value) {
        return only(asList(value));
    }

    public FilteredFacetExpression<T, V> only(final Iterable<V> values) {
        return new FilteredFacetExpression<>(this, typeSerializer, values, alias);
    }
}
