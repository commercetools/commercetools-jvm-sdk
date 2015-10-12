package io.sphere.sdk.search;

import io.sphere.sdk.expansion.MetaModelExpansionDsl;

import java.util.List;
import java.util.function.Function;

/**
 *
 * @param <T> type of the search result
 * @param <C> type of the class implementing this class
 * @param <S> type of the search model
 * @param <E> type of the expansion model
 */
public interface MetaModelSearchDsl<T, C extends MetaModelSearchDsl<T, C, S, E>, S, E> extends EntitySearch<T>, SearchDsl<T, C>, MetaModelExpansionDsl<T, C, E> {

    C withFacets(final Function<S, FacetExpression<T>> m);

    C plusFacets(final Function<S, FacetExpression<T>> m);

    C withResultFilters(final Function<S, List<FilterExpression<T>>> m);

    C plusResultFilters(final Function<S, List<FilterExpression<T>>> m);

    C withQueryFilters(final Function<S, List<FilterExpression<T>>> m);

    C plusQueryFilters(final Function<S, List<FilterExpression<T>>> m);

    C withFacetFilters(final Function<S, List<FilterExpression<T>>> m);

    C plusFacetFilters(final Function<S, List<FilterExpression<T>>> m);

    C plusFacetedSearch(final Function<S, FacetAndFilterSearchExpression<T>> m);

    C withSort(final Function<S, SortExpression<T>> m);

    C plusSort(final Function<S, SortExpression<T>> m);
}
