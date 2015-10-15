package io.sphere.sdk.search.model;

/**
 * Instantiates {@link SingleValueSortSearchModel} class
 * @param <T> type of the entity to sort by
 */
public class SingleValueSortSearchModelBuilder<T> implements SearchSortBuilder<T, SingleValueSortSearchModel<T>> {

    @Override
    public SingleValueSortSearchModel<T> apply(final SortableSearchModel<T, SingleValueSortSearchModel<T>> sortableSearchModel) {
        return new SingleValueSortSearchModel<>(sortableSearchModel);
    }
}
