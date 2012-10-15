package de.commercetools.internal;

import de.commercetools.sphere.client.FacetDefinition;
import de.commercetools.sphere.client.QueryParam;
import static de.commercetools.internal.util.QueryStringConstruction.*;

import java.util.List;
import java.util.Map;

/** Base class for facet definitions. */
public abstract class FacetDefinitionBase<T> implements FacetDefinition<T> {
    /** Name of the query parameter representing this facet. */
    protected String queryParam;

    protected FacetDefinitionBase(String queryParam) {
        this.queryParam = queryParam;
    }

    /** Returns application-URL representation of given item of this facet.
     *  Suitable for keeping state of selected items in application URL. */
    public abstract List<QueryParam> getUrlParams(T item);

    /** Adds representation of given item to application query string.
     *  Suitable for keeping state of selected items in application URL. */
    public final String getSelectLink(T item, Map<String, String[]> queryParams) {
        return addURLParams(queryParams, getUrlParams(item));
    }
    /** Removes representation of given from application query string.
     *  Suitable for keeping state of selected items in application URL. */
    public String getUnselectLink(T item, Map<String, String[]> queryParams) {
        return removeURLParams(queryParams, getUrlParams(item));
    }
    /** Checks whether given item is present in application query string.
     *  Suitable for keeping state of selected items in application URL. */
    public boolean isSelected(T item, Map<String, String[]> queryParams) {
        return containsAllURLParams(queryParams, getUrlParams(item));
    }
}
