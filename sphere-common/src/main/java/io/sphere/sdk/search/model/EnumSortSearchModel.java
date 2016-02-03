package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public final class EnumSortSearchModel<T, S extends SortSearchModel<T>> extends SortableSearchModel<T, S> {

    EnumSortSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SearchSortFactory<T, S> searchSortFactory) {
        super(parent, pathSegment, searchSortFactory);
    }

    public S key() {
        return sorted("key");
    }

    public S label() {
        return sorted("label");
    }
}
