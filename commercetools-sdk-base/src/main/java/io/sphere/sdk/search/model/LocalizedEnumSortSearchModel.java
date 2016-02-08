package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public final class LocalizedEnumSortSearchModel<T, S extends SortSearchModel<T>> extends SortableSearchModel<T, S> {

    LocalizedEnumSortSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SearchSortFactory<T, S> searchSortFactory) {
        super(parent, pathSegment, searchSortFactory);
    }

    public S key() {
        return sorted("key");
    }

    public LocalizedStringSortSearchModel<T, S> label() {
        return new LocalizedStringSortSearchModel<>(this, "label", searchSortFactory);
    }
}
