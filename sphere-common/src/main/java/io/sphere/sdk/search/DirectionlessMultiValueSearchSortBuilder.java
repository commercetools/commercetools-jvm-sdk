package io.sphere.sdk.search;

public class DirectionlessMultiValueSearchSortBuilder<T> implements SortBuilder<T, DirectionlessMultiValueSearchSortModel<T>> {

    @Override
    public DirectionlessMultiValueSearchSortModel<T> apply(final SortableSearchModel<T, DirectionlessMultiValueSearchSortModel<T>> sortableSearchModel) {
        return new DirectionlessMultiValueSearchSortModel<>(sortableSearchModel);
    }
}
