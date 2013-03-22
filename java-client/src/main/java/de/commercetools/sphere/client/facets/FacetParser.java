package io.sphere.client.facets;

import io.sphere.client.facets.Facet;
import io.sphere.client.facets.expressions.FacetExpression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FacetParser {
    /** Translates current query string into backend facet queries. */
    public static List<FacetExpression> parse(Map<String,String[]> queryString, Collection<Facet> facets) {
        List<FacetExpression> facetQueries = new ArrayList<FacetExpression>();
        for (Facet<?> facet: facets) {
            facetQueries.add(facet.parse(queryString));
        }
        return facetQueries;
    }
}
