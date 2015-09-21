package io.sphere.sdk.search;

import javax.annotation.Nullable;

public class EnumSearchModel<T, S extends DirectionlessSearchSortModel<T>> extends SortableSearchModel<T, S> {

    public EnumSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SortBuilder<T, S> sortBuilder) {
        super(parent, pathSegment, sortBuilder);
    }

    public StringSearchModel<T, S> key() {
        return new StringSearchModel<>(this, "key", sortBuilder);
    }

    public StringSearchModel<T, S> label() {
        return new StringSearchModel<>(this, "label", sortBuilder);
    }
}
