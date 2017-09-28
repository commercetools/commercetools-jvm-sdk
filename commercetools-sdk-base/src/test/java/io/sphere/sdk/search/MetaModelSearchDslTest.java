package io.sphere.sdk.search;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.models.LocalizedStringEntry;
import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

public class MetaModelSearchDslTest {
    private static final SortExpression<Object> SORT_EXPR_ONE = SortExpression.of("sort-one");
    private static final SortExpression<Object> SORT_EXPR_TWO = SortExpression.of("sort-two");
    private static final TermFacetExpression<Object> FACET_EXPR_ONE = TermFacetExpression.of("facet-one-term");
    private static final RangeFacetExpression<Object> FACET_EXPR_TWO = RangeFacetExpression.of("facet-two-range");
    private static final FilterExpression<Object> FILTER_EXPR_ONE = FilterExpression.of("filter-one");
    private static final FilterExpression<Object> FILTER_EXPR_TWO = FilterExpression.of("filter-two");
    private static final ExpansionPath<Object> EXPANSION_PATH_ONE = ExpansionPath.of("expansion-one");
    private static final ExpansionPath<Object> EXPANSION_PATH_TWO = ExpansionPath.of("expansion-two");
    private static final FacetedSearchExpression<Object> FACETED_SEARCH_EXPR_ONE = TermFacetedSearchExpression.of(FACET_EXPR_ONE, singletonList(FILTER_EXPR_ONE));
    private static final FacetedSearchExpression<Object> FACETED_SEARCH_EXPR_TWO = RangeFacetedSearchExpression.of(FACET_EXPR_TWO, singletonList(FILTER_EXPR_TWO));

    @Test
    public void buildsText() throws Exception {
        final TestableSearchDsl dsl = new TestableSearchDsl().withText(ENGLISH, "foo");
        assertThat(dsl.text()).isEqualTo(LocalizedStringEntry.of(ENGLISH, "foo"));
    }

    @Test
    public void buildsPagination() throws Exception {
        final TestableSearchDsl dsl = new TestableSearchDsl().withLimit(10).withOffset(5);
        assertThat(dsl.limit()).isEqualTo(10);
        assertThat(dsl.offset()).isEqualTo(5);
    }

    @Test
    public void buildsFacets() throws Exception {
        final TestableSearchDsl dsl = new TestableSearchDsl().withFacets(FACET_EXPR_ONE);
        assertThat(dsl.facets())
                .isEqualTo(singletonList(FACET_EXPR_ONE));
        assertThat(dsl.plusFacets(FACET_EXPR_TWO).facets())
                .isEqualTo(asList(FACET_EXPR_ONE, FACET_EXPR_TWO));
    }

    @Test
    public void buildsQueryFilters() throws Exception {
        final TestableSearchDsl dsl = new TestableSearchDsl().withQueryFilters(singletonList(FILTER_EXPR_ONE));
        assertThat(dsl.queryFilters())
                .isEqualTo(singletonList(FILTER_EXPR_ONE));
        assertThat(dsl.plusQueryFilters(singletonList(FILTER_EXPR_TWO)).queryFilters())
                .isEqualTo(asList(FILTER_EXPR_ONE, FILTER_EXPR_TWO));
    }

    @Test
    public void buildsResultFilters() throws Exception {
        final TestableSearchDsl dsl = new TestableSearchDsl().withResultFilters(singletonList(FILTER_EXPR_ONE));
        assertThat(dsl.resultFilters())
                .isEqualTo(singletonList(FILTER_EXPR_ONE));
        assertThat(dsl.plusResultFilters(singletonList(FILTER_EXPR_TWO)).resultFilters())
                .isEqualTo(asList(FILTER_EXPR_ONE, FILTER_EXPR_TWO));
    }

    @Test
    public void buildsFacetFilters() throws Exception {
        final TestableSearchDsl dsl = new TestableSearchDsl().withFacetFilters(singletonList(FILTER_EXPR_ONE));
        assertThat(dsl.facetFilters())
                .isEqualTo(singletonList(FILTER_EXPR_ONE));
        assertThat(dsl.plusFacetFilters(singletonList(FILTER_EXPR_TWO)).facetFilters())
                .isEqualTo(asList(FILTER_EXPR_ONE, FILTER_EXPR_TWO));
    }

    @Test
    public void buildsFacetedSearch() throws Exception {
        final TestableSearchDsl dsl = new TestableSearchDsl().withFacetedSearch(FACETED_SEARCH_EXPR_ONE);
        assertThat(dsl.facetedSearch())
                .isEqualTo(singletonList(FACETED_SEARCH_EXPR_ONE));
        assertThat(dsl.plusFacetedSearch(FACETED_SEARCH_EXPR_TWO).facetedSearch())
                .isEqualTo(asList(FACETED_SEARCH_EXPR_ONE, FACETED_SEARCH_EXPR_TWO));
    }

    @Test
    public void buildsSort() throws Exception {
        final TestableSearchDsl dsl = new TestableSearchDsl().withSort(SORT_EXPR_ONE);
        assertThat(dsl.sort())
                .isEqualTo(singletonList(SORT_EXPR_ONE));
        assertThat(dsl.plusSort(singletonList(SORT_EXPR_TWO)).sort())
                .isEqualTo(asList(SORT_EXPR_ONE, SORT_EXPR_TWO));
    }

    @Test
    public void buildsExpansion() throws Exception {
        final TestableSearchDsl dsl = new TestableSearchDsl().withExpansionPaths(EXPANSION_PATH_ONE);
        assertThat(dsl.expansionPaths())
                .isEqualTo(singletonList(EXPANSION_PATH_ONE));
        assertThat(dsl.plusExpansionPaths(singletonList(EXPANSION_PATH_TWO)).expansionPaths())
                .isEqualTo(asList(EXPANSION_PATH_ONE, EXPANSION_PATH_TWO));
    }

}
