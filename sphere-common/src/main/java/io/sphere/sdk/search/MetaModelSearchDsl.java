package io.sphere.sdk.search;

import io.sphere.sdk.expansion.MetaModelExpansionDsl;

/**
 *
 * @param <T> type of the search result
 * @param <C> type of the class implementing this class
 * @param <S> type of the sort model
 * @param <L> type of the filter model
 * @param <F> type of the facet model
 * @param <E> type of the expansion model
 */
public interface MetaModelSearchDsl<T, C extends MetaModelSearchDsl<T, C, S, L, F, E>, S, L, F, E> extends EntitySearch<T>, SearchDsl<T, C>,
        MetaModelSortDsl<T, C, S>, MetaModelFilterDsl<T, C, L>, MetaModelFacetDsl<T, C, F>, MetaModelExpansionDsl<T, C, E> {

}
