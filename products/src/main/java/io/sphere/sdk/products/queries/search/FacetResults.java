package io.sphere.sdk.products.queries.search;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Base;

import java.util.Map;

public class FacetResults extends Base {
    private final Map<String, FacetResult> facets;

    private FacetResults(final Map<String, FacetResult> facets) {
        this.facets = facets;
    }

    public Map<String, FacetResult> getFacets() {
        return facets;
    }

    public static FacetResults of(final Map<String, FacetResult> facets) {
        return new FacetResults(facets);
    }

    public static TypeReference<FacetResults> typeReference() {
        return new TypeReference<FacetResults>() {
            @Override
            public String toString() {
                return "TypeReference<FacetResults>";
            }
        };
    }
}
