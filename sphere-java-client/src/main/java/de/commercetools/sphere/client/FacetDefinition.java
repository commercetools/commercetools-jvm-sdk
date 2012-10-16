package de.commercetools.sphere.client;

import java.util.List;
import java.util.Map;

/** Facet 'component' that supports keeping the state of selected items in application's query string.
 *
 *  See {@link FacetParser} for reconstructing state of multiple facets from application's URL.
 *
 *  Use {#getUrlParams} to construct URLs for selecting individual facet items.
 *  You can also use helper methods {#getSelectLink}, {#getUnselectLink} and {#isSelected}
 *  that make use of {#getUrlParams}.
 *
 *  @param <T> Type of item in returned results.
 * */
public interface FacetDefinition<T> {
    /** The attribute for which this facet is aggregating counts. */
    String getAttributeName();

    /** Creates a backend facet query based on application's URL query parameters. */
    Facet parse(Map<String,String[]> queryString);

    /** Returns application-level URL representation for given item of this facet. */
    List<QueryParam> getUrlParams(T item);

    /** Adds representation of given item to application's query string. */
    String getSelectLink(T item, Map<String, String[]> queryParams);

    /** Removes representation of given item of this facet from application's query string. */
    String getUnselectLink(T item, Map<String, String[]> queryParams);

    /** Checks whether given item of this facet is present in application's query string. */
    boolean isSelected(T item, Map<String, String[]> queryParams);
}
