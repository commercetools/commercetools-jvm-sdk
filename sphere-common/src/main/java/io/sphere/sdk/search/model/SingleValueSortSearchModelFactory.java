package io.sphere.sdk.search.model;

/**
 * Instantiates {@link SingleValueSortSearchModel} class
 * @param <T> type of the resource to sort by
 */
public class SingleValueSortSearchModelFactory<T> implements SearchSortFactory<T, SingleValueSortSearchModel<T>> {

    private SingleValueSortSearchModelFactory() {
    }

    @Override
    public SingleValueSortSearchModel<T> apply(final SortableSearchModel<T, SingleValueSortSearchModel<T>> sortableSearchModel) {
        return new SingleValueSortSearchModel<>(sortableSearchModel);
    }

    public static <T> SingleValueSortSearchModelFactory<T> of() {
        return new SingleValueSortSearchModelFactory<>();
    }
}
