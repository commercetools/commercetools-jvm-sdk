package io.sphere.sdk.search;

import javax.annotation.Nullable;

public class UntypedSearchModel<T, S extends DirectionlessSearchSortModel<T>> extends RangeTermModelImpl<T, S, String> implements SearchSortModel<T, S> {

    public UntypedSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment, final SortBuilder<T, S> sortBuilder) {
        super(parent, pathSegment, sortBuilder);
    }

    @Override
    public RangedFilterSearchModel<T, String> filtered() {
        return new RangedFilterSearchModel<>(this, null, TypeSerializer.ofString());
    }

    @Override
    public RangedFacetSearchModel<T, String> faceted() {
        return new RangedFacetSearchModel<>(this, null, TypeSerializer.ofString());
    }

    @Override
    public S sorted() {
        return sortBuilder.apply(this);
    }
}
