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
    public RangeFilterSearchModel<T, V> filtered() {
        return new RangeFilterSearchModel<>(this, typeSerializer);
    }

    @Override
    public RangeFacetSearchModel<T, V> faceted() {
        return new RangeFacetSearchModel<>(this, typeSerializer);
    }

    @Override
    public RangeFacetAndFilterSearchModel<T, V> facetedAndFiltered() {
        return new RangeFacetAndFilterSearchModel<>(this, typeSerializer);
    }
}