package de.commercetools.sphere.client;

import java.util.Map;

/** Specifies a filter to be used when fetching data from the backend. */
public interface FilterDefinition {
    /** Creates a query to the Sphere backend based on URL query parameter values for this filter. */
    Filter parse(Map<String,String[]> queryString);
}
