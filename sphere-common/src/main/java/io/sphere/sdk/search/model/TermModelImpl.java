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
    public TermFilterSearchModel<T, V> filtered() {
        return new TermFilterSearchModel<>(this, typeSerializer);
    }

    @Override
    public TermFacetSearchModel<T, V> faceted() {
        return new TermFacetSearchModel<>(this, typeSerializer);
    }
}