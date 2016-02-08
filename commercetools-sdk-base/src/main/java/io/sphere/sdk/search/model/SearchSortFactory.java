package io.sphere.sdk.search.model;

import java.util.function.Function;

/**
 * A builder to instantiate any implementation of {@link SortSearchModel}.
 * @param <T> the resource to sort by
 * @param <S> the type of the class, implementation of {@link SortSearchModel}, where the element should be built
 */
@FunctionalInterface
interface SearchSortFactory<T, S extends SortSearchModel<T>> extends Function<SortableSearchModel<T, S>, S> {

    /**
     * Creates a new instance of S with the values of this builder and the given search model.
     * @param sortableSearchModel the given sortable search model
     * @return new instance of S
     */
    @Override
    S apply(final SortableSearchModel<T, S> sortableSearchModel);
}
