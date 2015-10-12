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
     * Returns an EntitySearch with the new facet expression list as facets (query parameter {@code facet}).
     * @param facetExpressions the new facet expression list
     * @return an EntitySearch with facets
     */
    C withFacets(final List<FacetExpression<T>> facetExpressions);

    C withFacets(final FacetExpression<T> facetExpression);

    /**
     * Returns an EntitySearch with the new facet expression list appended to the existing facets (query parameter {@code facet}).
     * @param facetExpressions the new facet expression list
     * @return an EntitySearch with the existing facets plus the new facet list.
     */
    C plusFacets(final List<FacetExpression<T>> facetExpressions);

    C plusFacets(final FacetExpression<T> facetExpression);

    /**
     * Returns an EntitySearch with the new result filter expression list as result filter (query parameter {@code filter}).
     * @param filterExpressions the new result filter expression list
     * @return an EntitySearch with resultFilters
     */
    C withResultFilters(final List<FilterExpression<T>> filterExpressions);

    /**
     * Returns an EntitySearch with the new result filter expression list appended to the existing result filters (query parameter {@code filter}).
     * @param filterExpressions the new result filter expression list
     * @return an EntitySearch with the existing result filter plus the new result filter list.
     */
    C plusResultFilters(final List<FilterExpression<T>> filterExpressions);

    /**
     * Returns an EntitySearch with the new query filter expression list as query filters (query parameter {@code filter.query}).
     * @param filterExpressions the new query filter expression list
     * @return an EntitySearch with queryFilters
     */
    C withQueryFilters(final List<FilterExpression<T>> filterExpressions);

    /**
     * Returns an EntitySearch with the new query filter expression list appended to the existing query filters (query parameter {@code filter.query}).
     * @param filterExpressions the new query filter expression list
     * @return an EntitySearch with the existing query filters plus the new query filter list.
     */
    C plusQueryFilters(final List<FilterExpression<T>> filterExpressions);

    /**
     * Returns an EntitySearch with the new facet filter list as facet filter (query parameter {@code filter.facet}).
     * @param filterExpressions the new facet filter expression list
     * @return an EntitySearch with facetFilters
     */
    C withFacetFilters(final List<FilterExpression<T>> filterExpressions);

    /**
     * Returns an EntitySearch with the new facet filter list appended to the existing facet filters (query parameter {@code filter.facet}).
     * @param filterExpressions the new facet filter expression list
     * @return an EntitySearch with the existing facet filters plus the new facet filter list.
     */
    C plusFacetFilters(final List<FilterExpression<T>> filterExpressions);

    /**
     * Returns an EntitySearch with the new faceted search expressions appended to the existing faceted search expressions.
     * A faceted expression is equivalent to use facet, facetFilters and resultFilters with the given facet and filters expressions.
     * Therefore calling afterwards {@link this#withFacets}, {@link this#withFacetFilters} or {@link this#withResultFilters} will break this feature!
     * @param facetAndFilterSearchExpression the new faceted search expression
     * @return an EntitySearch with the existing faceted search expressions plus the new faceted search expression.
     */
    C plusFacetedSearch(final FacetAndFilterSearchExpression<T> facetAndFilterSearchExpression);

    /**
     * Returns an EntityQuery with the new sort expressions.
     * @param sortExpressions the new sort expression list
     * @return EntityQuery with sort sortExpressions
     */
    C withSort(final List<SortExpression<T>> sortExpressions);

    C withSort(final SortExpression<T> sortExpression);

    /**
     * Returns an EntityQuery with the new sort expression list appended to the existing sort expressions.
     * @param sortExpressions the new sort expression list
     * @return an EntitySearch with the existing sort expressions plus the new sort expression list.
     */
    C plusSort(final List<SortExpression<T>> sortExpressions);

    C plusSort(final SortExpression<T> sortExpression);

    C withLimit(final long limit);

    C withOffset(final long offset);

    /**
     * @deprecated use {@link SearchDsl#withResultFilters(List)} instead
     */
    @Deprecated
    C withResultFilters(final FilterExpression<T> filterExpression);

    /**
     * @deprecated use {@link SearchDsl#plusResultFilters(List)} instead
     */
    @Deprecated
    C plusResultFilters(final FilterExpression<T> filterExpression);

    /**
     * @deprecated use {@link SearchDsl#withQueryFilters(List)} instead
     */
    @Deprecated
    C withQueryFilters(final FilterExpression<T> filterExpression);

    /**
     * @deprecated use {@link SearchDsl#plusQueryFilters(List)} instead
     */
    @Deprecated
    C plusQueryFilters(final FilterExpression<T> filterExpression);

    /**
     * @deprecated use {@link SearchDsl#withFacetFilters(List)} instead
     */
    @Deprecated
    C withFacetFilters(final FilterExpression<T> filterExpression);

    /**
     * @deprecated use {@link SearchDsl#plusFacetFilters(List)} instead
     */
    @Deprecated
    C plusFacetFilters(final FilterExpression<T> filterExpression);

}
