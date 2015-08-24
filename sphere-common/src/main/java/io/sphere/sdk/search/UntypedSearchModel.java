package io.sphere.sdk.search;

import javax.annotation.Nullable;

public class UntypedSearchModel<T> extends SearchModelImpl<T> implements RangeTermModel<T, String> {

    public UntypedSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangedFilterSearchModel<T, String> filtered() {
        return new RangedFilterSearchModel<>(this, null, TypeSerializer.ofString());
    }

    @Override
    public RangedFacetSearchModel<T, String> faceted() {
        return new RangedFacetSearchModel<>(this, null, TypeSerializer.ofString());
    }
}
