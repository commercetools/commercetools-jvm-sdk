package io.sphere.sdk.search.model;

/**
 * Instantiates {@link MultiValueSortSearchModel} class
 * @param <T> type of the resource to sort by
 */
public final class MultiValueSortSearchModelFactory<T> implements SearchSortFactory<T, MultiValueSortSearchModel<T>> {

    private MultiValueSortSearchModelFactory() {
    }

    @Override
    public MultiValueSortSearchModel<T> apply(final SortableSearchModel<T, MultiValueSortSearchModel<T>> sortableSearchModel) {
        return new MultiValueSortSearchModel<>(sortableSearchModel);
    }

    public static <T> MultiValueSortSearchModelFactory<T> of() {
        return new MultiValueSortSearchModelFactory<>();
    }
}
