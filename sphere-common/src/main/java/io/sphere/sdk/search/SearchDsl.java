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
