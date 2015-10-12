package io.sphere.sdk.search;

import javax.annotation.Nullable;

public class BooleanSearchModel<T, S extends DirectionlessSearchSortModel<T>> extends SortableTermModel<T, S, Boolean> implements SearchSortModel<T, S> {

    public BooleanSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SortBuilder<T, S> sortBuilder) {
        super(parent, pathSegment, sortBuilder);
    }

    @Override
    public FilterSearchModel<T, Boolean> filtered() {
        return new FilterSearchModel<>(this, null, TypeSerializer.ofBoolean());
    }

    @Override
    public FacetSearchModel<T, Boolean> faceted() {
        return new FacetSearchModel<>(this, null, TypeSerializer.ofBoolean());
    }

    @Override
    public FacetAndFilterSearchModel<T> facetedAndFiltered() {
        return super.facetedAndFiltered();
    }

    /**
     * Sorts boolean values in alphabetical order.
     * ASC: False, True
     * DESC: True, False
     */
    @Override
    public S sorted() {
        return super.sortModel();
    }
}
