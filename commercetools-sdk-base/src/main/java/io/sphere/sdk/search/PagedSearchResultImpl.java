package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.queries.PagedResultBase;
import io.sphere.sdk.search.model.RangeStats;
import io.sphere.sdk.search.model.SimpleRangeStats;

import java.util.List;
import java.util.Map;
import java.util.Optional;

final class PagedSearchResultImpl<T> extends PagedResultBase<T> implements PagedSearchResult<T> {

    private final Map<String, FacetResult> facets;

    @JsonCreator
    PagedSearchResultImpl(final Long offset, final Long limit, final Long total, final List<T> results, final Map<String, FacetResult> facets, final Long count) {
        super(offset, limit, total, results, count);
        this.facets = facets;
    }

    public Map<String, FacetResult> getFacetsResults() {
        return facets;
    }

    public FacetResult getFacetResult(final String facetResultPath) {
        return facets.get(facetResultPath);
    }

    public TermFacetResult getFacetResult(final TermFacetExpression<T> facetExpression) {
        return getTermFacetResult(facetExpression.resultPath());
    }

    public RangeFacetResult getFacetResult(final RangeFacetExpression<T> facetExpression) {
        return getRangeFacetResult(facetExpression.resultPath());
    }

    public FilteredFacetResult getFacetResult(final FilteredFacetExpression<T> facetExpression) {
        return getFilteredFacetResult(facetExpression.resultPath());
    }

    public TermFacetResult getFacetResult(final TermFacetedSearchExpression<T> facetedSearchExpression) {
        return getFacetResult(facetedSearchExpression.facetExpression());
    }

    public RangeFacetResult getFacetResult(final RangeFacetedSearchExpression<T> facetedSearchExpression) {
        return getFacetResult(facetedSearchExpression.facetExpression());
    }

    public SimpleRangeStats getRangeStatsOfAllRanges(final RangeFacetExpression<T> facetExpression) {
        final String facetResultPath = facetExpression.resultPath();
        final boolean facetIsOfTypeAllRanges = Optional.ofNullable(facetExpression.value())
                .map(v -> v.trim().equals(":range(* to \"0\"),(\"0\" to *)"))
                .orElse(false);
        if (facetIsOfTypeAllRanges) {
            final RangeFacetResult facetResult = getRangeFacetResult(facetResultPath);
            return getSimpleRangeStats(facetResult.getRanges());
        } else {
            throw new IllegalArgumentException("Facet result is not of type RangeFacetResult for all ranges, i.e. (* to \"0\"),(\"0\" to *): " + facetResultPath);
        }
    }

    public SimpleRangeStats getRangeStatsOfAllRanges(final RangeFacetedSearchExpression<T> facetedSearchExpression) {
        return getRangeStatsOfAllRanges(facetedSearchExpression.facetExpression());
    }

    public TermFacetResult getTermFacetResult(final String facetResultPath) {
        return Optional.ofNullable(getFacetResult(facetResultPath)).map(facetResult -> {
            if (facetResult instanceof TermFacetResult) {
                return (TermFacetResult) facetResult;
            } else {
                throw new IllegalArgumentException("Facet result is not of type TermFacetResult: " + facetResult);
            }
        }).orElse(null);
    }

    public RangeFacetResult getRangeFacetResult(final String facetResultPath) {
        return Optional.ofNullable(getFacetResult(facetResultPath)).map(facetResult -> {
            if (facetResult instanceof RangeFacetResult) {
                return (RangeFacetResult) facetResult;
            } else {
                throw new IllegalArgumentException("Facet result is not of type RangeFacetResult: " + facetResult);
            }
        }).orElse(null);
    }

    public FilteredFacetResult getFilteredFacetResult(final String facetResultPath) {
        return Optional.ofNullable(getFacetResult(facetResultPath)).map(facetResult -> {
            if (facetResult instanceof FilteredFacetResult) {
                return (FilteredFacetResult) facetResult;
            } else {
                throw new IllegalArgumentException("Facet result is not of type FilteredFacetResult: " + facetResult);
            }
        }).orElse(null);
    }

    private SimpleRangeStats getSimpleRangeStats(final List<RangeStats> ranges) {
        final RangeStats negativeRange = ranges.stream()
                .filter(r -> r.getLowerEndpoint() == null)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No range is of the form (* to \"0\")"));
        final RangeStats positiveRange = ranges.stream()
                .filter(r -> r.getUpperEndpoint() == null)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No range is of the form (\"0\" to *)"));
        final boolean hasNegativeValues = negativeRange.getCount() > 0;
        final boolean hasPositiveValues = positiveRange.getCount() > 0;
        final String min = hasNegativeValues ? negativeRange.getMin() : positiveRange.getMin();
        final String max = hasPositiveValues ? positiveRange.getMax() : negativeRange.getMax();
        final long count = ranges.stream()
                .mapToLong(r -> r.getCount())
                .sum();
        return SimpleRangeStats.of(null, null, count, min, max);
    }
}
