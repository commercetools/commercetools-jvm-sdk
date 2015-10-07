package io.sphere.sdk.search;

import javax.annotation.Nullable;

public class SortableSearchModel<T, S extends DirectionlessSearchSortModel<T>> extends SearchModelImpl<T> {
    protected final SortBuilder<T, S> sortBuilder;

    protected SortableSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment,
                                  final SortBuilder<T, S> sortBuilder) {
        super(parent, pathSegment);
        this.sortBuilder = sortBuilder;
    }

    protected S sortModel() {
        return sortModel(this);
    }

    protected S sortModel(final SortableSearchModel<T, S> searchModel) {
        return sortBuilder.apply(searchModel);
    }
}
