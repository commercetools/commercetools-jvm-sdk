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

    C withText(final Locale locale, final String text);

    /**
     * Returns an EntitySearch with the new facet list as facets.
     * @param facetExpressions the new facet expression list
     * @return an EntitySearch with facets
     */
    C withFacets(final List<FacetExpression<T>> facetExpressions);

    C withFacets(final FacetExpression<T> facetExpression);

    /**
     * Returns an EntitySearch with the new facet list appended to the existing facets.
     * @param facetExpressions the new facet expression list
     * @return an EntitySearch with the existing facets plus the new facet list.
     */
    C plusFacets(final List<FacetExpression<T>> facetExpressions);

    C plusFacets(final FacetExpression<T> facetExpression);

    /**
     * Returns an EntitySearch with the new result filter list as result filter.
     * @param resultFilterExpressions the new result filter expression list
     * @return an EntitySearch with resultFilters
     */
    C withResultFilters(final List<FilterExpression<T>> resultFilterExpressions);

    /**
     * Returns an EntitySearch with the new result filter list appended to the existing result filters.
     * @param resultFilterExpressions the new result filter expression list
     * @return an EntitySearch with the existing result filter plus the new result filter list.
     */
    C plusResultFilters(final List<FilterExpression<T>> resultFilterExpressions);

    /**
     * Returns an EntitySearch with the new query filter list as query filters.
     * @param queryFilterExpressions the new query filter expression list
     * @return an EntitySearch with queryFilters
     */
    C withQueryFilters(final List<FilterExpression<T>> queryFilterExpressions);

    /**
     * Returns an EntitySearch with the new query filter list appended to the existing query filters.
     * @param queryFilterExpressions the new query filter expression list
     * @return an EntitySearch with the existing query filters plus the new query filter list.
     */
    C plusQueryFilters(final List<FilterExpression<T>> queryFilterExpressions);

    /**
     * Returns an EntitySearch with the new facet filter list as facet filter.
     * @param facetFilterExpressions the new facet filter expression list
     * @return an EntitySearch with facetFilters
     */
    C withFacetFilters(final List<FilterExpression<T>> facetFilterExpressions);

    /**
     * Returns an EntitySearch with the new facet filter list appended to the existing facet filters.
     * @param facetFilterExpressions the new facet filter expression list
     * @return an EntitySearch with the existing facet filters plus the new facet filter list.
     */
    C plusFacetFilters(final List<FilterExpression<T>> facetFilterExpressions);

    /**
     * Returns an EntitySearch with the new faceted search expressions (i.e. facet, facetFilters and resultFilters expressions)
     * appended to the existing faceted search expressions.
     * @param facetedSearchExpression the new faceted search expression
     * @return an EntitySearch with the existing faceted search expressions plus the new faceted search expression.
     */
    C plusFacetedSearch(final FacetedSearchExpression<T> facetedSearchExpression);

    /**
     * Returns an EntityQuery with the new sort as sort.
     * @param sort how the results of the search should be sorted
     * @return EntityQuery with sort
     */
    C withSort(final SortExpression<T> sort);

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
