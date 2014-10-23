package io.sphere.sdk.products.queries.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.queries.PagedResult;

import java.util.List;

public class PagedSearchResult<T> extends PagedResult<T> {

    private final FacetResults facetResults;

    @JsonCreator
    PagedSearchResult(final int offset, final int total, final List<T> results, final FacetResults facetResults) {
        super(offset, total, results);
        this.facetResults = facetResults;
    }

    public FacetResults getFacetResults() {
        return facetResults;
    }
}
