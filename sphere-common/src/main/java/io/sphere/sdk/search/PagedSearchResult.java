package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.queries.PagedResult;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PagedSearchResult<T> extends PagedResult<T> {

    private final Map<String, FacetResult> facets;

    @JsonCreator
    PagedSearchResult(final Integer offset, final Integer total, final List<T> results, final Map<String, FacetResult> facets) {
        super(offset, total, results);
        this.facets = facets;
    }

    public Map<String, FacetResult> getFacetsResults() {
        return facets;
    }

    public FacetResult getFacetResult(final String facetResultPath) {
        return facets.get(facetResultPath);
    }

    public TermFacetResult getTermFacetResult(final TermFacetExpression<T> facetExpression) {
        return getTermFacetResult(facetExpression.resultPath());
    }

    public TermFacetResult getTermFacetResult(final String facetResultPath) {
        return Optional.ofNullable(getFacetResult(facetResultPath)).map(facetResult -> {
            if (facetResult instanceof TermFacetResult) {
                return (TermFacetResult) facetResult;
            } else {
                throw new ClassCastException("Facet result is not of type TermFacetResult: " + facetResult);
            }
        }).orElse(null);
    }

    public RangeFacetResult getRangeFacetResult(final RangeFacetExpression<T> facetExpression) {
        return getRangeFacetResult(facetExpression.resultPath());
    }

    public RangeFacetResult getRangeFacetResult(final String facetResultPath) {
        return Optional.ofNullable(getFacetResult(facetResultPath)).map(facetResult -> {
            if (facetResult instanceof RangeFacetResult) {
                return (RangeFacetResult) facetResult;
            } else {
                throw new ClassCastException("Facet result is not of type RangeFacetResult: " + facetResult);
            }
        }).orElse(null);
    }

    public FilteredFacetResult getFilteredFacetResult(final FilteredFacetExpression<T> facetExpression) {
        return getFilteredFacetResult(facetExpression.resultPath());
    }

    public FilteredFacetResult getFilteredFacetResult(final String facetResultPath) {
        return Optional.ofNullable(getFacetResult(facetResultPath)).map(facetResult -> {
            if (facetResult instanceof FilteredFacetResult) {
                return (FilteredFacetResult) facetResult;
            } else {
                throw new ClassCastException("Facet result is not of type FilteredFacetResult: " + facetResult);
            }
        }).orElse(null);
    }

    @Override
    public List<T> getResults() {
        return super.getResults();
    }

    @Override
    public Integer getOffset() {
        return super.getOffset();
    }

    @Override
    public int size() {
        return super.size();
    }

    @Override
    public Integer getTotal() {
        return super.getTotal();
    }

    @Override
    public Optional<T> head() {
        return super.head();
    }
}
