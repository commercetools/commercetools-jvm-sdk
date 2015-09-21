package io.sphere.sdk.search;

import javax.annotation.Nullable;

abstract class TermModelImpl<T, S extends DirectionlessSearchSortModel<T>, V> extends SortableSearchModel<T, S> implements TermModel<T, S, V> {

    TermModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SortBuilder<T, S> sortBuilder) {
        super(parent, pathSegment, sortBuilder);
    }

    @Override
    public UntypedSearchModel<T, S> untyped() {
        return new UntypedSearchModel<>(this, null, sortBuilder);
    }
}