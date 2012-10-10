package de.commercetools.sphere.client;

import java.util.Map;

public interface FacetDefinition {
    Facet parse(Map<String,String[]> queryString);
}
