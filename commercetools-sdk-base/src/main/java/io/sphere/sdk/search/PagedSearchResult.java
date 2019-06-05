package io.sphere.sdk.search;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.queries.PagedResult;
import io.sphere.sdk.search.model.RangeTermFacetedSearchSearchModel;
import io.sphere.sdk.search.model.SimpleRangeStats;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@JsonDeserialize(as = PagedSearchResultImpl.class)
public interface PagedSearchResult<T> extends PagedResult<T> {

    Map<String, FacetResult> getFacetsResults();


    /**
     * Creates a {@code {@link PagedSearchResult}} for search with no matching values.
     *
     * @param <T> the type of the underlying model
     * @return an empty {@code PagedSearchResult}
     */
    static <T> PagedSearchResultImpl<T> empty() {
        return new PagedSearchResultImpl<T>(0L, 20L, 0L, Collections.emptyList(), Collections.emptyMap(),0L);
    }
    
    /**
     * Obtains the {@link FacetResult} of the facet with the given result path.
     *
     * @param facetResultPath the facet result path, which is either the attribute path or the alias
     *
     * @return the facet result for the given facet or null
     */
    @Nullable
    FacetResult getFacetResult(final String facetResultPath);

    /**
     * Obtains the {@link TermFacetResult} of the facet with the given facet expression.
     *
     * @param facetExpression the facet expression
     *
     * @return the facet result for the given facet or null
     */
    @Nullable
    TermFacetResult getFacetResult(final TermFacetExpression<T> facetExpression);

    /**
     * Obtains the {@link RangeFacetResult} of the facet with the given facet expression.
     *
     * @param facetExpression the facet expression
     *
     * @return the facet result for the given facet or null
     */
    @Nullable
    RangeFacetResult getFacetResult(final RangeFacetExpression<T> facetExpression);

    /**
     * Obtains the {@link FilteredFacetResult} of the facet with the given facet expression.
     *
     * @param facetExpression the facet expression
     *
     * @return the facet result for the given facet or null
     */
    @Nullable
    FilteredFacetResult getFacetResult(final FilteredFacetExpression<T> facetExpression);

    /**
     * Obtains the {@link TermFacetResult} of the facet with the given facet search expression.
     *
     * @param facetedSearchExpression the facet search expression
     *
     * @return the facet result for the given facet or null
     */
    @Nullable
    TermFacetResult getFacetResult(final TermFacetedSearchExpression<T> facetedSearchExpression);

    /**
     * Obtains the {@link RangeFacetResult} of the facet with the given facet search expression.
     *
     * @param facetedSearchExpression the facet search expression
     *
     * @return the facet result for the given facet or null
     */
    @Nullable
    RangeFacetResult getFacetResult(final RangeFacetedSearchExpression<T> facetedSearchExpression);

    /**
     * Obtains the {@link SimpleRangeStats} of the range facet.
     *
     * This method should only be used when the range facet has the form {@code (* to "0"),("0" to *)},
     * which is obtained when calling {@link RangeTermFacetedSearchSearchModel#allRanges()}.
     *
     * @param facetExpression the range facet expression
     *
     * @return a {@link SimpleRangeStats} for the given range facet
     *
     * @throws IllegalArgumentException if the given range facet has an invalid form
     */
    SimpleRangeStats getRangeStatsOfAllRanges(final RangeFacetExpression<T> facetExpression);

    /**
     * Obtains the {@link SimpleRangeStats} of the range facet.
     *
     * This method should only be used when the range facet has the form {@code (* to "0"),("0" to *)},
     * which is obtained when calling {@link RangeTermFacetedSearchSearchModel#allRanges()}.
     *
     * @param facetedSearchExpression the range facet search expression
     *
     * @return a {@link SimpleRangeStats} for the given range facet
     *
     * @throws IllegalArgumentException if the given range facet has an invalid form
     */
    SimpleRangeStats getRangeStatsOfAllRanges(final RangeFacetedSearchExpression<T> facetedSearchExpression);

    /**
     * Obtains the {@link TermFacetResult} of the facet with the given facet expression.
     *
     * @param facetResultPath the facet result path
     *
     * @return the facet result for the given facet or null
     */
    @Nullable
    TermFacetResult getTermFacetResult(final String facetResultPath);

    /**
     * Obtains the {@link RangeFacetResult} of the facet with the given facet expression.
     *
     * @param facetResultPath the facet result path
     *
     * @return the facet result for the given facet or null
     */
    @Nullable
    RangeFacetResult getRangeFacetResult(final String facetResultPath);

    /**
     * Obtains the {@link FilteredFacetResult} of the facet with the given facet expression.
     *
     * @param facetResultPath the facet result path
     *
     * @return the facet result for the given facet or null
     */
    @Nullable
    FilteredFacetResult getFilteredFacetResult(final String facetResultPath);

    @Override
    List<T> getResults();

    @Override
    Long getOffset();

    @Override
    Long getTotal();
}
