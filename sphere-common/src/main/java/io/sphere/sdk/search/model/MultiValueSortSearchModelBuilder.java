package io.sphere.sdk.search.model;

/**
 * Instantiates {@link MultiValueSortSearchModel} class
 * @param <T> type of the entity to sort by
 */
public class MultiValueSortSearchModelBuilder<T> implements SearchSortBuilder<T, MultiValueSortSearchModel<T>> {

    @Override
    public MultiValueSortSearchModel<T> apply(final SortableSearchModel<T, MultiValueSortSearchModel<T>> sortableSearchModel) {
        return new MultiValueSortSearchModel<>(sortableSearchModel);
    }
}
