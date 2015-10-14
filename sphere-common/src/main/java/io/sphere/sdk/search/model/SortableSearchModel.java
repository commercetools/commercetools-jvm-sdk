package io.sphere.sdk.search.model;

import javax.annotation.Nullable;

public class SortableSearchModel<T, S extends SortSearchModel<T>> extends SearchModelImpl<T> {
    protected final SearchSortBuilder<T, S> searchSortBuilder;

    public SortableSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment,
                               final SearchSortBuilder<T, S> searchSortBuilder) {
        super(parent, pathSegment);
        this.searchSortBuilder = searchSortBuilder;
    }

    public S sorted() {
        return sorted(this);
    }

    public S sorted(final String pathSegment) {
        return new SortableSearchModel<>(this, pathSegment, searchSortBuilder).sorted();
    }

    public S sorted(final SortableSearchModel<T, S> searchModel) {
        return searchSortBuilder.apply(searchModel);
    }
}
