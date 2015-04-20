package io.sphere.sdk.search;

import io.sphere.sdk.queries.QueryParameter;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static io.sphere.sdk.utils.ListUtils.listOf;

public interface SearchDsl<T> extends EntitySearch<T> {

    /**
     * Returns an EntitySearch with the new text as search text.
     * @param text the new search text
     * @return an EntitySearch with text
     */
    SearchDsl<T> withText(final Optional<SearchText> text);

    /**
     * Returns an EntitySearch with the new facet list as facets.
     * @param facets the new facet list
     * @return an EntitySearch with facets
     */
    SearchDsl<T> withFacets(final List<FacetExpression<T>> facets);

    /**
     * Returns an EntitySearch with the new filter result list as filter results.
     * @param filterResults the new filter result list
     * @return an EntitySearch with filterResults
     */
    SearchDsl<T> withFilterResults(final List<FilterExpression<T>> filterResults);

    /**
     * Returns an EntitySearch with the new filter query list as filter queries.
     * @param filterQueries the new filter query list
     * @return an EntitySearch with filterQueries
     */
    SearchDsl<T> withFilterQuery(final List<FilterExpression<T>> filterQueries);

    /**
     * Returns an EntitySearch with the new filter facet list as filter facets.
     * @param filterFacets the new filter facet list
     * @return an EntitySearch with filterFacets
     */
    SearchDsl<T> withFilterFacets(final List<FilterExpression<T>> filterFacets);

//not yet implemented in the SPHERE.IO backend
//
//    /**
//     * Returns an EntityQuery with the new sort as sort.
//     * @param sort list of sorts how the results of the search should be sorted
//     * @return EntityQuery with sort
//     */
//    SearchDsl<T> withSort(final List<SearchSort<T>> sort);

    SearchDsl<T> withLimit(final long limit);

    SearchDsl<T> withOffset(final long offset);

    SearchDsl<T> withAdditionalQueryParameters(final List<QueryParameter> additionalQueryParameters);

    /**
     * Returns an EntitySearch with the new text as search text.
     * @param text the new search text
     * @return an EntitySearch with text
     */
    default SearchDsl<T> withText(final SearchText text) {
        return withText(Optional.of(text));
    }

    default SearchDsl<T> withText(final Locale locale, final String text) {
        return withText(SearchText.of(locale, text));
    }

    /**
     * Returns an EntitySearch with the new facet list appended to the existing facets.
     * @param facets the new facet list
     * @return an EntitySearch with the existing facets plus the new facet list.
     */
    default SearchDsl<T> plusFacets(final List<FacetExpression<T>> facets) {
        return withFacets(listOf(facets(), facets));
    }

    /**
     * Returns an EntitySearch with the new facet appended to the existing facets.
     * @param facet the new facet
     * @return an EntitySearch with the existing facets plus the new facet.
     */
    default SearchDsl<T> plusFacet(final FacetExpression<T> facet) {
        return withFacets(listOf(facets(), facet));
    }

    /**
     * Returns an EntitySearch with the new filter result list appended to the existing filter results.
     * @param filterResults the new filter result list
     * @return an EntitySearch with the existing filter results plus the new filter result list.
     */
    default SearchDsl<T> plusFilterResults(final List<FilterExpression<T>> filterResults) {
        return withFilterResults(listOf(filterResults(), filterResults));
    }

    /**
     * Returns an EntitySearch with the new filter result appended to the existing filter results.
     * @param filterResult the new filter result
     * @return an EntitySearch with the existing filter results plus the new filter result.
     */
    default SearchDsl<T> plusFilterResults(final FilterExpression<T> filterResult) {
        return withFilterResults(listOf(filterResults(), filterResult));
    }

    /**
     * Returns an EntitySearch with the new filter query list appended to the existing filter queries.
     * @param filterQueries the new filter query list
     * @return an EntitySearch with the existing filter queries plus the new filter query list.
     */
    default SearchDsl<T> plusFilterQuery(final List<FilterExpression<T>> filterQueries) {
        return withFilterQuery(listOf(filterQueries(), filterQueries));
    }

    /**
     * Returns an EntitySearch with the new filter query appended to the existing filter queries.
     * @param filterQuery the new filter query
     * @return an EntitySearch with the existing filter queries plus the new filter query.
     */
    default SearchDsl<T> plusFilterQuery(final FilterExpression<T> filterQuery) {
        return withFilterQuery(listOf(filterQueries(), filterQuery));
    }

    /**
     * Returns an EntitySearch with the new filter facet list appended to the existing filter facets.
     * @param filterFacets the new filter facet list
     * @return an EntitySearch with the existing filter facets plus the new filter facet list.
     */
    default SearchDsl<T> plusFilterFacets(final List<FilterExpression<T>> filterFacets) {
        return withFilterFacets(listOf(filterFacets(), filterFacets));
    }

    /**
     * Returns an EntitySearch with the new filter facet appended to the existing filter facets.
     * @param filterFacet the new filter facet
     * @return an EntitySearch with the existing filter facets plus the new filter facet.
     */
    default SearchDsl<T> plusFilterFacets(final FilterExpression<T> filterFacet) {
        return withFilterFacets(listOf(filterFacets(), filterFacet));
    }

    /**
     * Returns an EntityQuery with the new sort as sort.
     * @param sort how the results of the search should be sorted
     * @return EntityQuery with sort
     */
    SearchDsl<T> withSort(final SearchSort<T> sort);

    String endpoint();
}
