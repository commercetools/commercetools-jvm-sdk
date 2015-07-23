package io.sphere.sdk.search;

import javax.annotation.Nullable;
import java.util.Optional;

import static java.util.Arrays.asList;

public class FacetSearchModel<T, V> extends SearchModelImpl<T> {
    protected final Optional<String> alias;
    protected final TypeSerializer<V> typeSerializer;

    FacetSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment, final TypeSerializer<V> typeSerializer, final Optional<String> alias) {
        super(parent, pathSegment);
        this.alias = alias;
        this.typeSerializer = typeSerializer;
    }

    FacetSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment, final TypeSerializer<V> typeSerializer) {
        super(parent, pathSegment);
        this.alias = Optional.empty();
        this.typeSerializer = typeSerializer;
    }

    public FacetSearchModel<T, V> withAlias(final Optional<String> alias) {
        return new FacetSearchModel<>(this, null, typeSerializer, alias);
    }

    public FacetSearchModel<T, V> withAlias(final String alias) {
        return withAlias(Optional.of(alias));
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
