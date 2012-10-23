package de.commercetools.sphere.client;

import de.commercetools.sphere.client.model.*;

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
 *  @param <T> Type of facet items: {@link TermsFacetItem}, {@link ValuesFacetItem} or {RangesFacetItem}.
 * */
public interface FacetDefinition<T> {
    /** The attribute for which this facet is aggregating counts. */
    String getAttributeName();

    /** Creates a backend facet query based on application's URL query parameters. */
    Facet parse(Map<String,String[]> queryString);

    /** Returns application-level URL representation for given item of this facet. */
    List<QueryParam> getUrlParams(T item);

    /** Adds given item of this facet to application's query string (i.e. selects the item). */
    String getSelectLink(T item, Map<String, String[]> queryParams);

    /** Removes given item of this facet from application's query string (i.e. unselects the item). */
    String getUnselectLink(T item, Map<String, String[]> queryParams);

    /** Checks whether given item of this facet is present in application's query string (i.e. it is selected). */
    boolean isSelected(T item, Map<String, String[]> queryParams);

    /** Sets a custom query parameter name that will represent this filter in application's query string. */
    FacetDefinition<T> setQueryParam(String queryParam);
}
