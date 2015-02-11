package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.queries.PagedResult;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public Optional<FacetResult> getFacetResult(final String spherePath) {
        if (facets.containsKey(spherePath)) {
            return Optional.of(facets.get(spherePath));
        } else {
            return Optional.empty();
        }
    }

    @SuppressWarnings("unchecked")
    public <V> Optional<TermFacetResult<V>> getTermFacetResult(final TermFacetExpression<T, V> facetExpression) {
        return getFacetResult(facetExpression.searchPath()).map(facetResult -> {
            if (facetResult instanceof TermFacetResult) {
                return (TermFacetResult) facetResult;
            } else {
                // TODO Use another type of exception once exception task is finished
                throw new RuntimeException("Facet result is not of type TermFacetResult: " + facetResult);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <V extends Comparable<? super V>> Optional<RangeFacetResult<V>> getRangeFacetResult(final RangeFacetExpression<T, V> facetExpression) {
        return getFacetResult(facetExpression.searchPath()).map(facetResult -> {
            if (facetResult instanceof RangeFacetResult) {
                return (RangeFacetResult) facetResult;
            } else {
                // TODO Use another type of exception once exception task is finished
                throw new RuntimeException("Facet result is not of type RangeFacetResult: " + facetResult);
            }
        });
    }

    public Optional<FilterFacetResult> getFilteredFacetResult(final FilteredFacetExpression<T, ?> facetExpression) {
        return getFacetResult(facetExpression.searchPath()).map(facetResult -> {
            if (facetResult instanceof FilterFacetResult) {
                return (FilterFacetResult) facetResult;
            } else {
                // TODO Use another type of exception once exception task is finished
                throw new RuntimeException("Facet result is not of type FilterFacetResult: " + facetResult);
            }
        });
    }
}
