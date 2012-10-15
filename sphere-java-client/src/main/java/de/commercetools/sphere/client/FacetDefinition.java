package de.commercetools.sphere.client;

import java.util.List;
import java.util.Map;

public interface FacetDefinition<T> {
    /** The attribute for which this facet is aggregating counts. */
    String getAttributeName();

    /** Creates a concrete facet query based on URL query parameters. */
    Facet parse(Map<String,String[]> queryString);

    /** Returns application-URL representation of given item of this facet.
     *  Useful for keeping state of selected items in application URL. */
    List<QueryParam> getUrlParams(T item);

    /** Adds representation of given item to application query string.
     *  This method is useful for keeping state of selected items in application URL. */
    String getSelectLink(T item, Map<String, String[]> queryParams);

    /** Removes representation of given from application query string.
     *  This method is useful for keeping state of selected items in application URL. */
    String getUnselectLink(T item, Map<String, String[]> queryParams);

    /** Checks whether given item is present in application query string.
     *  This method is useful for keeping state of selected items in application URL. */
    boolean isSelected(T item, Map<String, String[]> queryParams);
}
