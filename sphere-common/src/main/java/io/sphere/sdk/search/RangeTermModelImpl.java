package io.sphere.sdk.search;

import javax.annotation.Nullable;

abstract class RangeTermModelImpl<T, S extends DirectionlessSearchSortModel<T>, V extends Comparable<? super V>> extends SortableSearchModel<T, S> implements RangeTermModel<T, S, V> {

    RangeTermModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SortBuilder<T, S> sortBuilder) {
        super(parent, pathSegment, sortBuilder);
    }

    @Override
    public UntypedSearchModel<T, S> untyped() {
        return new UntypedSearchModel<>(this, null, sortBuilder);
    }
}