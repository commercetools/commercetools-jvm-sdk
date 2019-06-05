package io.sphere.sdk.search;

import io.sphere.sdk.annotations.NotOSGiCompatible;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.search.model.RangeStats;
import io.sphere.sdk.search.model.SimpleRangeStats;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@NotOSGiCompatible
public class PagedSearchResultTest {
    private static final TermFacetExpression<Object> TERM_FACET_EXPR = TermFacetExpression.of("foo as termFacet");
    private static final RangeFacetExpression<Object> RANGE_FACET_EXPR = RangeFacetExpression.of("foo:range(0 to *) as rangeFacet");
    private static final FilteredFacetExpression<Object> FILTERED_FACET_EXPR = FilteredFacetExpression.of("foo:\"a\" as filteredFacet");

    private static final TermFacetResult TERM_FACET_RESULT = TermFacetResult.of(0L, 2L, 0L, asList(TermStats.of("a", 1L), TermStats.of("b", 2L)));
    private static final RangeFacetResult RANGE_FACET_RESULT = RangeFacetResult.of(singletonList(RangeStats.of(null, null, 4L, null, "3.0", "10.0", "20.0", 7.0)));
    private static final FilteredFacetResult FILTERED_FACET_RESULT = FilteredFacetResult.of(4L);

    @Test
    public void termFacetResult() throws Exception {
        testPagedSearchResult(result -> {
            assertThat(result.getFacetResult(TERM_FACET_EXPR)).isEqualTo(TERM_FACET_RESULT);
            assertThat(result.getTermFacetResult("non-existent")).isNull();
            assertThatThrownBy(() -> result.getTermFacetResult(RANGE_FACET_EXPR.resultPath()))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> result.getTermFacetResult(FILTERED_FACET_EXPR.resultPath()))
                    .isInstanceOf(IllegalArgumentException.class);
        });
    }

    @Test
    public void rangeFacetResult() throws Exception {
        testPagedSearchResult(result -> {
            assertThat(result.getFacetResult(RANGE_FACET_EXPR)).isEqualTo(RANGE_FACET_RESULT);
            assertThat(result.getRangeFacetResult("non-existent")).isNull();
            assertThatThrownBy(() -> result.getRangeFacetResult(TERM_FACET_EXPR.resultPath()))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> result.getRangeFacetResult(FILTERED_FACET_EXPR.resultPath()))
                    .isInstanceOf(IllegalArgumentException.class);
        });
    }

    @Test
    public void filteredFacetResult() throws Exception {
        testPagedSearchResult(result -> {
            assertThat(result.getFacetResult(FILTERED_FACET_EXPR)).isEqualTo(FILTERED_FACET_RESULT);
            assertThat(result.getFilteredFacetResult("non-existent")).isNull();
            assertThatThrownBy(() -> result.getFilteredFacetResult(TERM_FACET_EXPR.resultPath()))
                    .isInstanceOf(IllegalArgumentException.class);
            assertThatThrownBy(() -> result.getFilteredFacetResult(RANGE_FACET_EXPR.resultPath()))
                    .isInstanceOf(IllegalArgumentException.class);
        });
    }

    @Test
    public void rangeFacetOfAllRanges() throws Exception {
        testPagedSearchResultWithAllRanges(asList(
                        RangeStats.of(null, "0.0", 6L, null, "-10.0", "-3.0", "-20.0", -7.0),
                        RangeStats.of("0.0", null, 4L, null, "3.0", "10.0", "20.0", 7.0)),
                simpleRangeStats -> assertThat(simpleRangeStats).isEqualTo(SimpleRangeStats.of(null, null, 10L, "-10.0", "10.0")));
    }

    @Test
    public void rangeFacetOfAllRangesWithOnlyPositiveRange() throws Exception {
        testPagedSearchResultWithAllRanges(asList(
                        RangeStats.of(null, "0.0", 0L, null, "0.0", "0.0", "0.0", 0.0),
                        RangeStats.of("0.0", null, 4L, null, "3.0", "10.0", "20.0", 7.0)),
                simpleRangeStats -> assertThat(simpleRangeStats).isEqualTo(SimpleRangeStats.of(null, null, 4L, "3.0", "10.0")));
    }

    @Test
    public void rangeFacetOfAllRangesWithOnlyNegativeRange() throws Exception {
        testPagedSearchResultWithAllRanges(asList(
                        RangeStats.of(null, "0.0", 6L, null, "-10.0", "-3.0", "-20.0", -7.0),
                        RangeStats.of("0.0", null, 0L, null, "0.0", "0.0", "0.0", 0.0)),
                simpleRangeStats -> assertThat(simpleRangeStats).isEqualTo(SimpleRangeStats.of(null, null, 6L, "-10.0", "-3.0")));
    }
    
    @Test
    public void emptyResultCanBeSerializedToJson() throws Exception {
        final PagedSearchResult<String> result = PagedSearchResult.empty();
        final String resultJson = SphereJsonUtils.toJsonString(result);
        assertThat(resultJson).isNotEmpty();
    }
    
    
    private <T> void testPagedSearchResult(final Consumer<PagedSearchResult<T>> test) {
        final Map<String, FacetResult> facets = new HashMap<>();
        facets.put(TERM_FACET_EXPR.resultPath(), TERM_FACET_RESULT);
        facets.put(RANGE_FACET_EXPR.resultPath(), RANGE_FACET_RESULT);
        facets.put(FILTERED_FACET_EXPR.resultPath(), FILTERED_FACET_RESULT);
        final PagedSearchResult<T> result = pagedSearchResult(facets);
        test.accept(result);
    }

    private void testPagedSearchResultWithAllRanges(final List<RangeStats> rangeStats, final Consumer<SimpleRangeStats> test) {
        final RangeFacetExpression<Object> facetExpression = RangeFacetExpression.of("foo:range(* to \"0\"),(\"0\" to *)  as allRangesFacet");
        final Map<String, FacetResult> facets = new HashMap<>();
        facets.put(facetExpression.resultPath(), RangeFacetResult.of(rangeStats));
        final PagedSearchResult<Object> result = pagedSearchResult(facets);
        test.accept(result.getRangeStatsOfAllRanges(facetExpression));
    }

    private <T> PagedSearchResult<T> pagedSearchResult(final Map<String, FacetResult> facets) {
        return new PagedSearchResultImpl<>(0L, 0L, 0L, emptyList(), facets, 0L);
    }
}
