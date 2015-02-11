package io.sphere.sdk.search;

import java.util.Optional;

import static java.util.Arrays.asList;

public class TermFacetSearchModel<T, V> extends SearchModelImpl<T> {
    protected TypeSerializer<V> typeSerializer;

    TermFacetSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment, final TypeSerializer<V> typeSerializer) {
        super(parent, pathSegment);
        this.typeSerializer = typeSerializer;
    }

    public TermFacetExpression<T, V> all() {
        return new TermFacetExpression<>(this, asList(), typeSerializer);
    }

    public FilteredFacetExpression<T, V> only(final V value) {
        return only(asList(value));
    }

    public FilteredFacetExpression<T, V> only(final Iterable<V> values) {
        return new FilteredFacetExpression<>(this, values, typeSerializer);
    }
}
