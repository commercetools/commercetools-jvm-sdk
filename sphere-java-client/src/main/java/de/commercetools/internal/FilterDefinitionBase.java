package de.commercetools.internal;

import de.commercetools.sphere.client.Filter;
import de.commercetools.sphere.client.FilterDefinition;
import de.commercetools.sphere.client.Filters;

import java.util.Map;

/** Base class for filter definitions. */
public abstract class FilterDefinitionBase implements FilterDefinition {
    /** Name of the query parameter representing this filter. */
    protected String queryParam;

    protected FilterDefinitionBase(String queryParam) {
        this.queryParam = queryParam;
    }

    public Filter parse(Map<String,String[]> queryString) {
        return Filters.none();
    }
}
