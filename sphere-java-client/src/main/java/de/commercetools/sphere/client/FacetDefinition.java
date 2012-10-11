package de.commercetools.sphere.client;

import java.util.Map;

public interface FacetDefinition {
    /** The attribute for which this facet is aggregating counts. */
    String getAttributeName();

    // no getQueryParamName() because it's completely up to the facet how it serializes itself into the URL
    /** Creates a concrete facet query based on URL query parameters. */
    Facet parse(Map<String,String[]> queryString);
}
