package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.queries.PagedResult;

import java.util.List;
import java.util.Map;

public class PagedSearchResult<T> extends PagedResult<T> {

    private final Map<String, FacetResult> facets;

    @JsonCreator
    PagedSearchResult(final int offset, final int total, final List<T> results, final Map<String, FacetResult> facets) {
        super(offset, total, results);
        this.facets = facets;
    }

    public Map<String, FacetResult> getFacetsResults() {
        return facets;
    }
}
