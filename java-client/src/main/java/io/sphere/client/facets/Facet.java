package io.sphere.client.facets;

import io.sphere.client.facets.expressions.FacetExpression;
import io.sphere.client.QueryParam;

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
 *  @param <T> Type of items returned for this facet:
 *  {@link io.sphere.client.model.facets.TermFacetItem},
 *  {@link io.sphere.client.model.facets.RangeFacetItem}. */
public interface Facet<T> {
    // Backend query construction
    /** The attribute for which this facet is aggregating counts. */
    String getAttributeName();

    /** Creates a backend facet query based on application's URL query parameters. */
    FacetExpression parse(Map<String,String[]> queryString);

    // URL construction
    /** Sets a custom query parameter name that will represent this facet in application's query string. */
    Facet<T> setQueryParam(String queryParam);

    /** If set to true, the user will only be able to select a single value at a time.
     *  The default is false which means multiple values can be selected at the same time.
     *
     *  The value of {@code isSingleSelect} influences the behavior of {@link #getSelectLink}. */
    public Facet<T> setSingleSelect(boolean isSingleSelect);

    /** Returns application-level URL representation for given item of this facet. */
    List<QueryParam> getUrlParams(T item);

    /** Checks whether given item of this facet is present in application's query string (i.e. it is selected). */
    boolean isSelected(T item, Map<String, String[]> queryParams);

    /** Adds given item of this facet to application's query string (i.e. selects the item). */
    String getSelectLink(T item, Map<String, String[]> queryParams);

    /** Removes given item of this facet from application's query string (i.e. unselects the item). */
    String getUnselectLink(T item, Map<String, String[]> queryParams);
}
