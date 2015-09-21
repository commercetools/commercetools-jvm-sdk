package io.sphere.sdk.search;

import javax.annotation.Nullable;

public class ReferenceSearchModel<T, S extends DirectionlessSearchSortModel<T>> extends SortableSearchModel<T, S> {

    public ReferenceSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SortBuilder<T, S> sortBuilder) {
        super(parent, pathSegment, sortBuilder);
    }

    public StringSearchModel<T, S> id() {
        return new StringSearchModel<>(this, "id", sortBuilder);
    }
}
