package io.sphere.sdk.search;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.queries.PagedResult;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;
import io.sphere.sdk.search.model.SimpleRangeStats;

import java.util.List;
import java.util.Map;

@JsonDeserialize(as = PagedSearchResultImpl.class)
public interface PagedSearchResult<T> extends PagedResult<T> {

    Map<String, FacetResult> getFacetsResults();

    /**
     * Obtains the {@code FacetResult} of the facet with the given result path.
     * @param facetResultPath the facet result path, which is either the attribute path or the alias
     * @return the facet result for that facet
     */
    FacetResult getFacetResult(final String facetResultPath);

    TermFacetResult getFacetResult(final TermFacetExpression<T> facetExpression);

    RangeFacetResult getFacetResult(final RangeFacetExpression<T> facetExpression);

    FilteredFacetResult getFacetResult(final FilteredFacetExpression<T> facetExpression);

    TermFacetResult getFacetResult(final TermFacetedSearchExpression<T> facetedSearchExpression);

    RangeFacetResult getFacetResult(final RangeFacetedSearchExpression<T> facetedSearchExpression);

    /**
     * Obtains the {@code RangeStats} of the range facet.
     * This method should only be used when the range facet has the form {@code (* to "0"),("0" to *)},
     * which is obtained when calling {@link RangeTermFacetedSearchSearchModel#allRanges()}.
     * @param facetExpression the range facet expression
     * @return a {@code SimpleRangeStats} for the given range facet
     */
    SimpleRangeStats getRangeStatsOfAllRanges(final RangeFacetExpression<T> facetExpression);

    SimpleRangeStats getRangeStatsOfAllRanges(final RangeFacetedSearchExpression<T> facetedSearchExpression);

    TermFacetResult getTermFacetResult(final String facetResultPath);

    RangeFacetResult getRangeFacetResult(final String facetResultPath);

    FilteredFacetResult getFilteredFacetResult(final String facetResultPath);

    @Override
    List<T> getResults();

    @Override
    Long getOffset();

    @Override
    Long getTotal();
}
