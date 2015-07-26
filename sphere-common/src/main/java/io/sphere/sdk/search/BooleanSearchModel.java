package io.sphere.sdk.search;

import javax.annotation.Nullable;

public class BooleanSearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> implements TermModel<T, Boolean>, SearchSortingModel<T, S> {

    public BooleanSearchModel(@Nullable final SearchModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public FilterSearchModel<T, Boolean> filterBy() {
        return new FilterSearchModel<>(this, null, TypeSerializer.ofBoolean());
    }

    @Override
    public FacetSearchModel<T, Boolean> facetOf() {
        return new FacetSearchModel<>(this, null, TypeSerializer.ofBoolean());
    }

    /**
     * Sorts boolean values in alphabetical order.
     * ASC: False, True
     * DESC: True, False
     */
    @Override
    public SearchSort<T> sort(final S sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}
