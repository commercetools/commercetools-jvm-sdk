package de.commercetools.sphere.client;

import java.util.List;
import java.util.Map;

/** Filter 'component' that supports user selection from multiple predefined values and
 *  keeping the state of selected values in application's URL query string.
 *
 *  See {@link FilterParser} for reconstructing state of multiple filters from application's URL.
 *
 *  Use {#getUrlParams} to construct URLs for selecting individual filter values.
 *  You can also use helper methods {#getSelectLink}, {#getUnselectLink} and {#isSelected}
 *  that make use of {#getUrlParams}.
 *
 *  <p>
 *  Note:
 *  This class is quite similar to {@link FacetDefinition}.
 *  The difference is that filters simply filter results while facets do filtering and also result count aggregation.
 *  Performance-wise, filters are cheaper than facets.
 *
 *  See also: {@link UserInputFilterDefinition}
 *
 *  @param <T> Type of individual values that can be selected by the user (e.g. String, Double, BigDecimal, Range&lt;Double&gt; etc.).
 * */
public interface MultiSelectFilterDefinition<T> extends FilterDefinition {
    /** Returns application-level URL representation for given value of this filter. */
    List<QueryParam> getUrlParams(T value);

    /** The values that user can choose from. */
    List<T> getValues();

    /** Adds representation of given value to application's query string. */
    String getSelectLink(T value, Map<String, String[]> queryParams);

    /** Removes representation of given value of this filter from application's query string. */
    String getUnselectLink(T value, Map<String, String[]> queryParams);

    /** Checks whether given value of this filter is present in application's query string. */
    boolean isSelected(T value, Map<String, String[]> queryParams);

    /** If set to true, the user will only be able to select a single value at a time.
     *  The default is false which means multiple values can be selected at the same time.
     *
     *  The value of {@param isSingleSelect} influences the behavior of {@link #getSelectLink}. */
    public MultiSelectFilterDefinition<T> setSingleSelect(boolean isSingleSelect);
}
