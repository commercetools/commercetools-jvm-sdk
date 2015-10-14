package io.sphere.sdk.search.model;

public class SingleValueSortSearchModelBuilder<T> implements SearchSortBuilder<T, SingleValueSortSearchModel<T>> {

    @Override
    public SingleValueSortSearchModel<T> apply(final SortableSearchModel<T, SingleValueSortSearchModel<T>> sortableSearchModel) {
        return new SingleValueSortSearchModel<>(sortableSearchModel);
    }
}
