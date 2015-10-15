package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public class EnumSortSearchModel<T, S extends SortSearchModel<T>> extends SortableSearchModel<T, S> {

    public EnumSortSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment, final SearchSortBuilder<T, S> searchSortBuilder) {
        super(parent, pathSegment, searchSortBuilder);
    }

    public S key() {
        return sorted("key");
    }

    public S label() {
        return sorted("label");
    }
}
