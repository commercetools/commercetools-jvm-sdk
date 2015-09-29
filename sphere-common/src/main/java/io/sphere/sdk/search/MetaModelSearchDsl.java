package io.sphere.sdk.search;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.LocalizedStringEntry;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;

/**
 *
 * @param <T> type of the search result
 * @param <C> type of the class implementing this class
 * @param <S> type of the search model
 * @param <E> type of the expansion model
 */
public interface MetaModelSearchDsl<T, C extends MetaModelSearchDsl<T, C, S, E>, S, E> extends EntitySearch<T>, SearchDsl<T, C>, MetaModelExpansionDsl<T, C, E> {

    @Override
    C withText(final LocalizedStringEntry text);

    @Override
    C withFuzzy(final Boolean fuzzy);

    @Override
    C withText(final Locale locale, final String text);

    @Override
    C withFacets(final List<FacetExpression<T>> facets);

    @Override
    C withFacets(final FacetExpression<T> facet);

    C withFacets(final Function<S, FacetExpression<T>> m);

    @Override
    C plusFacets(final List<FacetExpression<T>> facets);

    @Override
    C plusFacets(final FacetExpression<T> facet);

    C plusFacets(final Function<S, FacetExpression<T>> m);

    @Override
    C withResultFilters(final List<FilterExpression<T>> resultFilters);

    @Override
    C withResultFilters(final FilterExpression<T> resultFilter);

    C withResultFilters(final Function<S, FilterExpression<T>> m);

    @Override
    C plusResultFilters(final List<FilterExpression<T>> resultFilters);

    @Override
    C plusResultFilters(final FilterExpression<T> resultFilter);

    C plusResultFilters(final Function<S, FilterExpression<T>> m);

    @Override
    C withQueryFilters(final List<FilterExpression<T>> queryFilters);

    @Override
    C withQueryFilters(final FilterExpression<T> queryFilter);

    C withQueryFilters(final Function<S, FilterExpression<T>> m);

    @Override
    C plusQueryFilters(final List<FilterExpression<T>> queryFilters);

    @Override
    C plusQueryFilters(final FilterExpression<T> queryFilter);

    C plusQueryFilters(final Function<S, FilterExpression<T>> m);

    @Override
    C withFacetFilters(final List<FilterExpression<T>> facetFilters);

    @Override
    C withFacetFilters(final FilterExpression<T> facetFilter);

    C withFacetFilters(final Function<S, FilterExpression<T>> m);

    @Override
    C plusFacetFilters(final List<FilterExpression<T>> facetFilters);

    @Override
    C plusFacetFilters(final FilterExpression<T> facetFilter);

    C plusFacetFilters(final Function<S, FilterExpression<T>> m);

    @Override
    C withSort(final SearchSort<T> sort);

    C withSort(final Function<S, SearchSort<T>> m);

    @Override
    C withLimit(final long limit);

    @Override
    C withOffset(final long offset);

    @Override
    C withExpansionPaths(final Function<E, ExpansionPath<T>> m);

    @Override
    C plusExpansionPaths(final Function<E, ExpansionPath<T>> m);
}
