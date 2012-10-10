package de.commercetools.internal;

import de.commercetools.sphere.client.FacetDefinition;

/** Base class for facet definitions. */
public abstract class FacetDefinitionBase implements FacetDefinition {
    /** Name of the query parameter representing this facet. */
    protected String queryParam;

    protected FacetDefinitionBase(String queryParam) {
        this.queryParam = queryParam;
    }
}
