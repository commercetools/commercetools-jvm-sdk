package io.sphere.sdk.search;

import javax.annotation.Nullable;

public class StringSearchModel<T, S extends DirectionlessSearchSortModel<T>> extends SortableTermModel<T, S, String> implements SearchSortModel<T, S> {

    public StringSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SortBuilder<T, S> sortBuilder) {
        super(parent, pathSegment, sortBuilder);
    }

    @Override
    public FilterSearchModel<T, String> filtered() {
        return new FilterSearchModel<>(this, null, TypeSerializer.ofString());
    }

    @Override
    public FacetSearchModel<T, String> faceted() {
        return new FacetSearchModel<>(this, null, TypeSerializer.ofString());
    }

    @Override
    public FacetAndFilterSearchModel<T> facetedAndFiltered() {
        return super.facetedAndFiltered();
    }

    @Override
    public S sorted() {
        return super.sortModel();
    }
}