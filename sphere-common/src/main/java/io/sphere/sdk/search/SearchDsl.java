package io.sphere.sdk.search;

import io.sphere.sdk.models.LocalizedStringEntry;

import java.util.List;
import java.util.Locale;

public interface SearchDsl<T, C extends SearchDsl<T, C>> extends EntitySearch<T> {

    /**
     * Returns an EntitySearch with the new text as search text.
     * @param text the new search text
     * @return an EntitySearch with text
     */
    C withText(final LocalizedStringEntry text);

    /**
     * Returns an EntitySearch with mofified fuzzyParameter.
     * @param fuzzy a flag to indicate if fuzzy search is enabled (true) or not (false)
     * @return an EntitySearch with the new fuzzy flag setting
     */
    C withFuzzy(final Boolean fuzzy);

    C withText(final Locale locale, final String text);

    /**
     * Returns an EntitySearch with the new facet list as facets.
     * @param facets the new facet list
     * @return an EntitySearch with facets
     */
    C withFacets(final List<FacetExpression<T>> facets);

    C withFacets(final FacetExpression<T> facet);

    /**
     * Returns an EntitySearch with the new facet list appended to the existing facets.
     * @param facets the new facet list
     * @return an EntitySearch with the existing facets plus the new facet list.
     */
    C plusFacets(final List<FacetExpression<T>> facets);

    C plusFacets(final FacetExpression<T> facet);

    /**
     * Returns an EntitySearch with the new result filter list as result filter.
     * @param resultFilters the new result filter list
     * @return an EntitySearch with resultFilters
     */
    C withResultFilters(final List<FilterExpression<T>> resultFilters);

    C withResultFilters(final FilterExpression<T> resultFilter);

    /**
     * Returns an EntitySearch with the new result filter list appended to the existing result filters.
     * @param resultFilters the new result filter list
     * @return an EntitySearch with the existing result filter plus the new result filter list.
     */
    C plusResultFilters(final List<FilterExpression<T>> resultFilters);

    C plusResultFilters(final FilterExpression<T> resultFilter);

    /**
     * Returns an EntitySearch with the new query filter list as query filters.
     * @param queryFilters the new query filter list
     * @return an EntitySearch with queryFilters
     */
    C withQueryFilters(final List<FilterExpression<T>> queryFilters);

    C withQueryFilters(final FilterExpression<T> queryFilter);

    /**
     * Returns an EntitySearch with the new query filter list appended to the existing query filters.
     * @param queryFilters the new query filter list
     * @return an EntitySearch with the existing query filters plus the new query filter list.
     */
    C plusQueryFilters(final List<FilterExpression<T>> queryFilters);

    C plusQueryFilters(final FilterExpression<T> queryFilter);

    /**
     * Returns an EntitySearch with the new facet filter list as facet filter.
     * @param facetFilters the new facet filter list
     * @return an EntitySearch with facetFilters
     */
    C withFacetFilters(final List<FilterExpression<T>> facetFilters);

    C withFacetFilters(final FilterExpression<T> facetFilter);

    /**
     * Returns an EntitySearch with the new facet filter list appended to the existing facet filters.
     * @param facetFilters the new facet filter list
     * @return an EntitySearch with the existing facet filters plus the new facet filter list.
     */
    C plusFacetFilters(final List<FilterExpression<T>> facetFilters);

    C plusFacetFilters(final FilterExpression<T> facetFilter);

    /**
     * Returns an EntityQuery with the new sort as sort.
     * @param sort how the results of the search should be sorted
     * @return EntityQuery with sort
     */
    C withSort(final SearchSort<T> sort);

    //not yet implemented in the SPHERE.IO backend
    //
    //    /**
    //     * Returns an EntityQuery with the new sort as sort.
    //     * @param sort list of sorts how the results of the search should be sorted
    //     * @return EntityQuery with sort
    //     */
    //    C withSort(final List<SearchSort<T>> sort);

    C withLimit(final long limit);

    C withOffset(final long offset);

}
