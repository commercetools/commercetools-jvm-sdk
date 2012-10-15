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

    /** {@inheritDoc} */
    public abstract List<QueryParam> getUrlParams(T item);
    /** {@inheritDoc} */
    public final String getSelectLink(T item, Map<String, String[]> queryParams) {
        return addURLParams(queryParams, getUrlParams(item));
    }
    /** {@inheritDoc} */
    public String getUnselectLink(T item, Map<String, String[]> queryParams) {
        return removeURLParams(queryParams, getUrlParams(item));
    }
    /** {@inheritDoc} */
    public boolean isSelected(T item, Map<String, String[]> queryParams) {
        return containsAllURLParams(queryParams, getUrlParams(item));
    }
}
