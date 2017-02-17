package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import java.util.function.Function;

abstract class TermModelImpl<T, V> extends SearchModelImpl<T> implements TermModel<T, V> {
    private final Function<V, String> typeSerializer;

    TermModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final Function<V, String> typeSerializer) {
        super(parent, pathSegment);
        this.typeSerializer = typeSerializer;
    }

    @Override
    public TermFilterExistsAndMissingSearchModel<T, V> filtered() {
        return new TermFilterSearchModelImpl<>(this, typeSerializer);
    }

    @Override
    public TermFacetSearchModel<T, V> faceted() {
        return new TermFacetSearchModelImpl<>(this, typeSerializer);
    }

    @Override
    public TermFacetedSearchSearchModel<T> facetedAndFiltered() {
        return new TermFacetedSearchSearchModel<>(this);
    }
}