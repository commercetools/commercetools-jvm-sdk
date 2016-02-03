package io.sphere.sdk.search.model;

import io.sphere.sdk.models.Base;

/**
 * Instantiates {@link SingleValueSortSearchModel} class
 * @param <T> type of the resource to sort by
 */
public final class SingleValueSortSearchModelFactory<T> extends Base implements SearchSortFactory<T, SingleValueSortSearchModel<T>> {

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
