package io.sphere.sdk.products.queries.search;

import io.sphere.sdk.queries.QueryParameter;

import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.utils.ListUtils.listOf;
import static java.util.Arrays.asList;

public interface SearchDsl<T> extends EntitySearch<T> {

    /**
     * Returns an EntitySearch with the new text as search text.
     * @param text the new search text
     * @return an EntitySearch with text
     */
    SearchDsl<T> withText(final Optional<String> text);

    /**
     * Returns an EntitySearch with the new facet list as facets.
     * @param facets the new facet list
     * @return an EntitySearch with facets
     */
    SearchDsl<T> withFacets(final List<Facet<T>> facets);

    /**
     * Returns an EntitySearch with the new filter list as filters.
     * @param filters the new filter list
     * @return an EntitySearch with filters
     */
    SearchDsl<T> withFilters(final List<Filter<T>> filters);

    /**
     * Returns an EntitySearch with the new filter query list as filter queries.
     * @param filterQueries the new filter query list
     * @return an EntitySearch with filterQueries
     */
    SearchDsl<T> withFilterQueries(final List<Filter<T>> filterQueries);

    /**
     * Returns an EntitySearch with the new filter facet list as filter facets.
     * @param filterFacets the new filter facet list
     * @return an EntitySearch with filterFacets
     */
    SearchDsl<T> withFilterFacets(final List<Filter<T>> filterFacets);

    /**
     * Returns an EntityQuery with the new sort as sort.
     * @param sort list of sorts how the results of the search should be sorted
     * @return EntityQuery with sort
     */
    SearchDsl<T> withSort(final List<SearchSort<T>> sort);

    SearchDsl<T> withLimit(final long limit);

    SearchDsl<T> withOffset(final long offset);

    SearchDsl<T> withAdditionalQueryParameters(final List<QueryParameter> additionalQueryParameters);

    /**
     * Returns an EntitySearch with the new text as search text.
     * @param text the new search text
     * @return an EntitySearch with text
     */
    default SearchDsl<T> withText(final String text) {
        return withText(Optional.of(text));
    }

    /**
     * Returns an EntitySearch with the new facet list appended to the existing facets.
     * @param facets the new facet list
     * @return an EntitySearch with the existing facets plus the new facet list.
     */
    default SearchDsl<T> plusFacets(final List<Facet<T>> facets) {
        return withFacets(listOf(facets(), facets));
    }

    /**
     * Returns an EntitySearch with the new facet appended to the existing facets.
     * @param facet the new facet
     * @return an EntitySearch with the existing facets plus the new facet.
     */
    default SearchDsl<T> plusFacet(final Facet<T> facet) {
        return withFacets(listOf(facets(), facet));
    }

    /**
     * Returns an EntitySearch with the new filter list appended to the existing filters.
     * @param filters the new filter list
     * @return an EntitySearch with the existing filters plus the new filter list.
     */
    default SearchDsl<T> plusFilters(final List<Filter<T>> filters) {
        return withFilters(listOf(filters(), filters));
    }

    /**
     * Returns an EntitySearch with the new filter appended to the existing filters.
     * @param filter the new filter
     * @return an EntitySearch with the existing filters plus the new filter.
     */
    default SearchDsl<T> plusFilter(final Filter<T> filter) {
        return withFilters(listOf(filters(), filter));
    }

    /**
     * Returns an EntitySearch with the new filter query list appended to the existing filter queries.
     * @param filterQueries the new filter query list
     * @return an EntitySearch with the existing filter queries plus the new filter query list.
     */
    default SearchDsl<T> plusFilterQueries(final List<Filter<T>> filterQueries) {
        return withFilterQueries(listOf(filterQueries(), filterQueries));
    }

    /**
     * Returns an EntitySearch with the new filter query appended to the existing filter queries.
     * @param filterQuery the new filter query
     * @return an EntitySearch with the existing filter queries plus the new filter query.
     */
    default SearchDsl<T> plusFilterQuery(final Filter<T> filterQuery) {
        return withFilterQueries(listOf(filterQueries(), filterQuery));
    }

    /**
     * Returns an EntitySearch with the new filter facet list appended to the existing filter facets.
     * @param filterFacets the new filter facet list
     * @return an EntitySearch with the existing filter facets plus the new filter facet list.
     */
    default SearchDsl<T> plusFilterFacets(final List<Filter<T>> filterFacets) {
        return withFilterFacets(listOf(filterFacets(), filterFacets));
    }

    /**
     * Returns an EntitySearch with the new filter facet appended to the existing filter facets.
     * @param filterFacet the new filter facet
     * @return an EntitySearch with the existing filter facets plus the new filter facet.
     */
    default SearchDsl<T> plusFilterFacet(final Filter<T> filterFacet) {
        return withFilterFacets(listOf(filterFacets(), filterFacet));
    }

    /**
     * Returns an EntityQuery with the new sort as sort.
     * @param sort how the results of the search should be sorted
     * @return EntityQuery with sort
     */
    default SearchDsl<T> withSort(final SearchSort<T> sort) {
        return withSort(asList(sort));
    }


    String endpoint();
}
