package io.sphere.sdk.search;

import javax.annotation.Nullable;

public class BooleanSearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> implements TermModel<T, Boolean>, SearchSortingModel<T, S> {

    public BooleanSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public FilterSearchModel<T, Boolean> filtered() {
        return new FilterSearchModel<>(this, null, TypeSerializer.ofBoolean());
    }

    @Override
    public FacetSearchModel<T, Boolean> faceted() {
        return new FacetSearchModel<>(this, null, TypeSerializer.ofBoolean());
    }

    /**
     * Sorts boolean values in alphabetical order.
     * ASC: False, True
     * DESC: True, False
     */
    @Override
    public SearchSort<T> sorted(final S sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}
