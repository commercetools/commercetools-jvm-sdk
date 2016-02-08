package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import java.util.function.Function;

class RangeTermModelImpl<T, V extends Comparable<? super V>> extends SearchModelImpl<T> implements RangeTermModel<T, V> {
    private final Function<V, String> typeSerializer;

    RangeTermModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final Function<V, String> typeSerializer) {
        super(parent, pathSegment);
        this.typeSerializer = typeSerializer;
    }

    @Override
    public RangeTermFilterSearchModel<T, V> filtered() {
        return new RangeTermFilterSearchModel<>(this, typeSerializer);
    }

    @Override
    public RangeTermFacetSearchModel<T, V> faceted() {
        return new RangeTermFacetSearchModel<>(this, typeSerializer);
    }

    @Override
    public RangeTermFacetedSearchSearchModel<T> facetedAndFiltered() {
        return new RangeTermFacetedSearchSearchModel<>(this);
    }
}