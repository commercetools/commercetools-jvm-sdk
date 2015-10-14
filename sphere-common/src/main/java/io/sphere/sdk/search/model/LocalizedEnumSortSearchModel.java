package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public class LocalizedEnumSortSearchModel<T, S extends SortSearchModel<T>> extends SortableSearchModel<T, S> {

    public LocalizedEnumSortSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment,
                                        final SearchSortBuilder<T, S> searchSortBuilder) {
        super(parent, pathSegment, searchSortBuilder);
    }

    public S key() {
        return sorted("key");
    }

    public LocalizedStringSortSearchModel<T, S> label() {
        return new LocalizedStringSortSearchModel<>(this, "label", searchSortBuilder);
    }
}
