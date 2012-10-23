package de.commercetools.internal;

import de.commercetools.sphere.client.FacetDefinition;
import de.commercetools.sphere.client.QueryParam;

import java.util.List;
import java.util.Map;

import static de.commercetools.internal.util.QueryStringConstruction.addURLParams;
import static de.commercetools.internal.util.QueryStringConstruction.containsAllURLParams;
import static de.commercetools.internal.util.QueryStringConstruction.removeURLParams;

/** Definition of a facet that matches on a custom attribute. */
public abstract class FacetDefinitionBase<T> implements FacetDefinition<T> {
    /** Name of the application-level query parameter for this facet. */
    protected String queryParam;
    /** Backend name of the custom attribute. */
    protected String attribute;
    /** The attribute on which this facet matches and aggregates counts. */
    public String getAttributeName() {
        return attribute;
    }

    /** Creates a new instance of facet definition. */
    protected FacetDefinitionBase(String attribute) {
        this.attribute = attribute;
        this.queryParam = attribute;
    }

    /** {@inheritDoc} */
    public abstract List<QueryParam> getUrlParams(T item);
    /** {@inheritDoc} */
    @Override public final String getSelectLink(T item, Map<String, String[]> queryParams) {
        return addURLParams(queryParams, getUrlParams(item));
    }
    /** {@inheritDoc} */
    @Override public final String getUnselectLink(T item, Map<String, String[]> queryParams) {
        return removeURLParams(queryParams, getUrlParams(item));
    }
    /** {@inheritDoc} */
    @Override public final boolean isSelected(T item, Map<String, String[]> queryParams) {
        return containsAllURLParams(queryParams, getUrlParams(item));
    }
}