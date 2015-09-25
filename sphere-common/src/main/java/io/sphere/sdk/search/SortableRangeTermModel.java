package io.sphere.sdk.search;

import javax.annotation.Nullable;

abstract class SortableRangeTermModel<T, S extends DirectionlessSearchSortModel<T>, V extends Comparable<? super V>> extends SortableSearchModel<T, S> implements RangeTermModel<T, V> {

    SortableRangeTermModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SortBuilder<T, S> sortBuilder) {
        super(parent, pathSegment, sortBuilder);
    }

    @Override
    public FacetedSearchModel<T> facetedSearch() {
        return new FacetedSearchModel<>(this, null);
    }
}