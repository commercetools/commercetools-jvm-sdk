package io.sphere.sdk.search;

import javax.annotation.Nullable;

abstract class SortableTermModel<T, S extends DirectionlessSearchSortModel<T>, V extends Comparable<? super V>> extends SortableSearchModel<T, S> implements TermModel<T, V> {

    SortableTermModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SortBuilder<T, S> sortBuilder) {
        super(parent, pathSegment, sortBuilder);
    }

    @Override
    public FacetAndFilterSearchModel<T> facetedAndFiltered() {
        return new FacetAndFilterSearchModel<>(this, null);
    }
}