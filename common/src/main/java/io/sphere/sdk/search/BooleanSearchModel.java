package io.sphere.sdk.search;

import java.util.Optional;

public class BooleanSearchModel<T, S extends SearchSortDirection> extends SearchModelImpl<T> implements TermModel<T, Boolean>, SearchSortingModel<T, S> {

    public BooleanSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public FilterSearchModel<T, Boolean> filter() {
        return new FilterSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofBoolean());
    }

    @Override
    public FacetSearchModel<T, Boolean> facet() {
        return new FacetSearchModel<>(Optional.of(this), Optional.empty(), TypeSerializer.ofBoolean());
    }

    /**
     * Sorts boolean values in alphabetical order.
     * ASC: False, True
     * DESC: True, False
     */
    @Override
    public SearchSort<T> sort(S sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }
}
