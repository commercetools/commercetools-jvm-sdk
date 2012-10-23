package de.commercetools.sphere.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FacetParser {

    public static List<Facet> parse(Map<String,String[]> queryString, Collection<FacetDefinition> facetDefinitions) {
        List<Facet> facetQueries = new ArrayList<Facet>();
        for (FacetDefinition<?> facet: facetDefinitions) {
            facetQueries.add(facet.parse(queryString));
        }
        return facetQueries;
    }
}