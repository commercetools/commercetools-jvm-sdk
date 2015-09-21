package io.sphere.sdk.search;

import javax.annotation.Nullable;

public class LocalizedEnumSearchModel<T, S extends DirectionlessSearchSortModel<T>> extends SortableSearchModel<T, S> {

    public LocalizedEnumSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SortBuilder<T, S> sortBuilder) {
        super(parent, pathSegment, sortBuilder);
    }

    public StringSearchModel<T, S> key() {
        return new StringSearchModel<>(this, "key", sortBuilder);
    }

    public LocalizedStringSearchModel<T, S> label() {
        return new LocalizedStringSearchModel<>(this, "label", sortBuilder);
    }
}
