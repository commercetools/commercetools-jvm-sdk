package io.sphere.sdk.search;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedStringEntry;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;

/**
 * @param <B> type of the builder
 * @param <T> type of the search result
 * @param <C> type of the class implementing the search
 * @param <S> type of the sort model
 * @param <L> type of the filter model
 * @param <F> type of the facet model
 * @param <E> type of the expansion model
 */
public interface ResourceMetaModelSearchDslBuilder<B, T, C extends MetaModelSearchDsl<T, C, S, L, F, E>, S, L, F, E> extends Builder<C> {
    B text(final LocalizedStringEntry text);

    B text(final Locale locale, final String text);

    B fuzzy(final Boolean fuzzy);

    B fuzzyLevel(final Integer fuzzyLevel);

    B facets(final List<FacetExpression<T>> facets);

    B facets(final Function<F, FacetExpression<T>> m);

    B plusFacets(final Function<F, FacetExpression<T>> m);

    B plusResultFilters(final List<FilterExpression<T>> filterExpressions);

    B plusResultFilters(final Function<L, List<FilterExpression<T>>> m);

    B plusQueryFilters(final List<FilterExpression<T>> filterExpressions);

    B plusQueryFilters(final Function<L, List<FilterExpression<T>>> m);

    B plusFacetFilters(final List<FilterExpression<T>> filterExpressions);

    B plusFacetFilters(final Function<L, List<FilterExpression<T>>> m);

    B plusSort(final List<SortExpression<T>> sortExpressions);

    B plusSort(final SortExpression<T> sortExpression);

    B plusSort(final Function<S, SortExpression<T>> m);

    B limit(final Long limit);

    B limit(final long limit);

    B offset(final Long offset);

    B offset(final long offset);

    B plusExpansionPaths(final List<ExpansionPath<T>> expansionPaths);

    B plusExpansionPaths(final ExpansionPath<T> expansionPath);

    B plusFacetedSearch(final FacetedSearchExpression<T> facetedSearchExpression);

    B plusFacetedSearch(final List<FacetedSearchExpression<T>> facetedSearchExpressions);

    B facetedSearch(final FacetedSearchExpression<T> facetedSearchExpression);

    B facetedSearch(final List<FacetedSearchExpression<T>> facetedSearchExpressions);

    B queryFilters(final List<FilterExpression<T>> filterExpressions);

    B resultFilters(final List<FilterExpression<T>> filterExpressions);

    B plusExpansionPaths(final Function<E, ExpansionPathContainer<T>> m);

    B expansionPaths(final Function<E, ExpansionPathContainer<T>> m);

    B facetFilters(final Function<L, List<FilterExpression<T>>> m);

    B queryFilters(final Function<L, List<FilterExpression<T>>> m);

    B resultFilters(final Function<L, List<FilterExpression<T>>> m);

    B plusFacets(final FacetExpression<T> facetExpression);

    B plusFacets(final List<FacetExpression<T>> facetExpressions);

    B facets(final FacetExpression<T> facetExpression);

    B sort(final Function<S, SortExpression<T>> m);

    B facetFilters(final List<FilterExpression<T>> filterExpressions);

    B plusExpansionPaths(ExpansionPathContainer<T> holder);

    B expansionPaths(final ExpansionPath<T> expansionPath);

    B expansionPaths(final List<ExpansionPath<T>> expansionPaths);

    B expansionPaths(ExpansionPathContainer<T> holder);

    B sort(final SortExpression<T> sortExpression);

    B sort(final List<SortExpression<T>> sortExpressions);

}
