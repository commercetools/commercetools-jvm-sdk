package de.commercetools.sphere.client.util;

import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import com.google.common.util.concurrent.ListenableFuture;
import de.commercetools.sphere.client.BackendException;
import de.commercetools.sphere.client.model.SearchResult;
import org.joda.time.*;

import java.util.Collection;

/** Represents a search request to the Sphere backend.
 *  Use {@link #fetch} or {@link #fetchAsync} to execute a request to backend. */
public interface SearchRequestBuilder<T> {
    /** Executes the request to the Sphere backend and returns a result. */
    SearchResult<T> fetch() throws BackendException;

    /** Executes the request in a non-blocking way and returns a future that provides a notification
     *  when the results from the Sphere backend arrive. */
    ListenableFuture<SearchResult<T>> fetchAsync() throws BackendException;

    /** Sets the page number for paging through results. Page numbers start at zero. */
    SearchRequestBuilder<T> page(int page);

    /** Sets the size of a page for paging through results. When page size is not set, the default of 10 is used. */
    SearchRequestBuilder<T> pageSize(int pageSize);

    /** Requests references to be expanded in the returned JSON documents.
     *  Expanded references contain the full target objects they link to.
     *
     *  @param paths The paths to be expanded, e.g. 'vendor', 'categories[*]' or 'variants[*].vendor'. */
    SearchRequestBuilder<T> expand(String... paths);

    // ---------------------------------------
    // Facet
    // ---------------------------------------

    /** Requests that the result contain aggregated counts of search results matching given facet expression.
     * The aggregated counts are returned as {@link SearchResult#getTermsFacet(String)}.
     *
     * @throws IllegalArgumentException If the expression is null or empty.
     *
     * @param expression The facet expression for which aggregated counts of search results should be calculated,
     *                   e.g. 'attributes.color', or 'variant.attributes.color'. */
    SearchRequestBuilder<T> facet(String expression);

    /** Requests that the result contain aggregated counts of search results matching given facet expression.
     * The aggregated counts are returned as {@link SearchResult#getRangeFacet(String)}
     *
     * @throws IllegalArgumentException If the expression is null or empty.
     *
     * @param expression The facet expression for which aggregated counts of search results should be calculated,
     *                   e.g. 'variant.attributes.height'. 
     * @param ranges Ranges for which aggregated counts should be calculated. Sphere treats ranges as closed,
     *               therefore it's best to use {@link Ranges.closed} to construct the ranges. */
    SearchRequestBuilder<T> facetDoubleRanges(String expression, Collection<Range<Double>> ranges);

    /** Requests that the result contain aggregated counts of search results matching given facet expression.
     * The aggregated counts are returned as {@link SearchResult#getRangeFacet(String)}
     *
     * @throws IllegalArgumentException If the expression is null or empty.
     *
     * @param expression The facet expression for which aggregated counts of search results should be calculated,
     *                   e.g. 'variant.attributes.productionDate' (note that dates are encoded as 'yyyy-mm-dd').
     * @param ranges Ranges for which aggregated counts should be calculated. Sphere treats ranges as closed,
     *               therefore it's best to use {@link Ranges.closed} to construct the ranges. */
    SearchRequestBuilder<T> facetStringRanges(String expression, Collection<Range<String>> ranges);

    SearchRequestBuilder<T> facetDateRanges(String expression, Collection<Range<LocalDate>> ranges);

    SearchRequestBuilder<T> multiSelectStringFacet(String expression, Collection<String> values);

    SearchRequestBuilder<T> multiSelectDateRangeFacet(String expression, Collection<Range<LocalDate>> values, Collection<Range<LocalDate>> allValues);

    // ---------------------------------------
    // Filters
    // ---------------------------------------

    /** Adds a filter. This filter does nothing if the value is null or empty.
     *
     * @param path The path to be matched, e.g. 'categories.id', 'attributes.color', or 'variant.attributes.color'.
     * @param value The value to search for. */
    SearchRequestBuilder<T> filter(String path, String value);

    /** Adds a filter. This filter does nothing if the value is null.
     *
     * @param path The path to be matched e.g. 'variant.attributes.height'.
     * @param value The value to search for. */
    SearchRequestBuilder<T> filter(String path, Double value);

    /** Searches for money values in a given range. This filter does nothing if the value is null.
     *
     * @param path The path to be matched, e.g. 'price', 'attributes.variant.cost'.
     * @param value The value to search for. */
    SearchRequestBuilder<T> filterMoney(String path, Double value);

    // ---------------------------------------
    // Filters with type
    // ---------------------------------------

    /** Adds a filter. This filter does nothing if the value is null or empty.
     *
     * @param path The path to be matched, e.g. 'categories.id', 'attributes.color', or 'variant.attributes.color'.
     * @param value The value to search for.
     * @param filterType The way the filter influences facet counts. */
    SearchRequestBuilder<T> filter(String path, String value, FilterType filterType);

    /** Adds a filter. This filter does nothing if the value is null.
     *
     * @param path The path to be matched e.g. 'variant.attributes.height'.
     * @param value The value to search for. 
     * @param filterType The way the filter influences facets counts. */
    SearchRequestBuilder<T> filter(String path, Double value, FilterType filterType);

    /** Adds a filter for money. This filter does nothing if the value is null.
     *
     * @param path The path to be matched, e.g. 'price', 'attributes.variant.cost'.
     * @param value The value to search for. 
     * @param filterType The way the filter influences facets counts. */
    SearchRequestBuilder<T> filterMoney(String path, Double value, FilterType filterType);

    // ---------------------------------------
    // Range filters
    // ---------------------------------------

    /** Searches for values in a given range. This filter does nothing if both endpoints of the range are null.
     *
     * @throws IllegalArgumentException If the expression is null or empty.
     *
     * @param path The path to be matched, e.g. 'variant.attributes.height'.
     * @param range The lower bound of the range to search for, both endpoints inclusive. */
    SearchRequestBuilder<T> filterRange(String path, Range<Double> range);

    SearchRequestBuilder<T> filterStringRange(String path, Range<String> range);

    /** Searches for values in any of given ranges. This filter does nothing the range collection is empty.
     *
     * @param path The path to be matched, e.g. 'variant.attributes.height'.
     * @param ranges The ranges to search for, both endpoints inclusive. */
    SearchRequestBuilder<T> filterRanges(String path, Collection<Range<Double>> ranges);

    /** Searches for values in any of given ranges. This filter does nothing the range collection is empty.
     *
     * @param path The path to be matched, e.g. 'variant.attributes.height'.
     * @param ranges The ranges to search for. */
    SearchRequestBuilder<T> filterDateRanges(String path, Collection<Range<LocalDate>> ranges);

    /** Searches for money values in a given range. This filter does nothing if both of the range endpoints are null.
     *
     * @param path The path to be matched, e.g. 'price', 'attributes.variant.cost'.
     * @param range The range to search for, both endpoints inclusive. */
    SearchRequestBuilder<T> filterMoneyRange(String path, Range<Double> range);

    /** Searches for money values in any of given ranges. This filter does nothing if the ranges collection is empty.
     *
     * @param path The path to be matched, e.g. 'price', 'attributes.variant.cost'.
     * @param ranges The money ranges to search for, both endpoints inclusive. */
    SearchRequestBuilder<T> filterMoneyRanges(String path, Collection<Range<Double>> ranges);

    // ---------------------------------------
    // Range filters with type
    // ---------------------------------------

    /** Searches for values in a given range. This filter does nothing if both bounds of the range are null.
     *
     * @param path The path to be matched, e.g. 'variant.attributes.height'.
     * @param range The range to search for.
     * @param filterType The way the filter influences facets counts. */           
    SearchRequestBuilder<T> filterRange(String path, Range<Double> range, FilterType filterType);

    /** Searches for values in any of given ranges. This filter does nothing the range collection is empty.
     *
     * @param path The path to be matched, e.g. 'variant.attributes.height'.
     * @param ranges The ranges to search for.
     * @param filterType The way the filter influences facets counts. */
    SearchRequestBuilder<T> filterRanges(String path, Collection<Range<Double>> ranges, FilterType filterType);

    /** Searches for values in any of given ranges. This filter does nothing the range collection is empty.
     *
     * @param path The path to be matched, e.g. 'variant.attributes.height'.
     * @param ranges The ranges to search for.
     * @param filterType The way the filter influences facets counts. */
    SearchRequestBuilder<T> filterDateRanges(String path, Collection<Range<LocalDate>> ranges, FilterType filterType);

    /** Searches for money values in a given range. This filter does nothing if both bounds of the range are null.
     *
     * @param path The path to be matched, e.g. 'price', 'attributes.variant.cost'.
     * @param range The money ranges to search for.
     * @param filterType The way the filter influences facets counts. */           
    SearchRequestBuilder<T> filterMoneyRange(String path, Range<Double> range, FilterType filterType);

    /** Searches for money values in any of given ranges. This filter does nothing the ranges collection is empty.
     *
     * @param path The path to be matched, e.g. 'price', 'attributes.variant.cost'.
     * @param ranges The money ranges to search for.
     * @param filterType The way the filter influences facets counts. */
    SearchRequestBuilder<T> filterMoneyRanges(String path, Collection<Range<Double>> ranges, FilterType filterType);

    // ---------------------------------------
    // Multiple value filters
    // ---------------------------------------

    /** Adds a multiple value OR filter. This filter does nothing if values are empty.
     *
     * @param path The path to be matched, e.g. 'categories.id', 'attributes.color', or 'variant.attributes.color'.
     * @param values Search for any of these values. */
    SearchRequestBuilder<T> filterAnyString(String path, Collection<String> values);

    /** Adds a multiple value OR filter. This filter does nothing if values are empty.
     *
     * @param path The path to be matched e.g. 'variant.attributes.height'.
     * @param values Search for any of these values. */
    SearchRequestBuilder<T> filterAnyDouble(String path, Collection<Double> values);

    // filterAnyMoney

    // ---------------------------------------
    // Multiple value filters with type
    // ---------------------------------------

    /** Adds a multiple value OR filter. This filter does nothing if values are empty.
     *
     * @param path The path to be matched, e.g. 'categories.id', 'attributes.color', or 'variant.attributes.color'.
     * @param values Search for any of these values. 
     * @param filterType The way the filter influences facets counts.*/
    SearchRequestBuilder<T> filterAnyString(String path, Collection<String> values, FilterType filterType);

    /** Adds a multiple value OR filter. This filter does nothing if values are empty.
     *
     * @param path The path to be matched e.g. 'variant.attributes.height'.
     * @param values Search for any of these values. 
     * @param filterType The way the filter influences facets counts.*/
    SearchRequestBuilder<T> filterAnyDouble(String path, Collection<Double> values, FilterType filterType);

    // filterAnyMoney
}
