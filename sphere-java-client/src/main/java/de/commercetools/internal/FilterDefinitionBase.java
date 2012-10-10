package de.commercetools.internal;

import de.commercetools.sphere.client.FilterDefinition;

/** Base class for filter implementations. */
public abstract class FilterDefinitionBase implements FilterDefinition {
    /** Name of the query parameter representing this filter. */
    protected String queryParam;

    protected FilterDefinitionBase(String queryParam) {
        this.queryParam = queryParam;
    }

    public String getQueryParamName() {
        return this.queryParam;
    }
}
