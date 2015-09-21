package io.sphere.sdk.search;

public class DirectionlessSearchSortBuilder<T> implements SortBuilder<T, DirectionlessSearchSortModel<T>> {

    @Override
    public DirectionlessSearchSortModel<T> apply(final SortableSearchModel<T, DirectionlessSearchSortModel<T>> sortableSearchModel) {
        return new DirectionlessSearchSortModelImpl<>(sortableSearchModel);
    }
}
