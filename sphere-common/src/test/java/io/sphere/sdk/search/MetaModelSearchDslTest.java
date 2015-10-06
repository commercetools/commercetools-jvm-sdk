package io.sphere.sdk.search;

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
    private static final FacetExpression<Object> FACET_EXPR_ONE = FacetExpression.of("facet-one");
    private static final FacetExpression<Object> FACET_EXPR_TWO = FacetExpression.of("facet-two");
    private static final FilterExpression<Object> FILTER_EXPR_ONE = FilterExpression.of("filter-one");
    private static final FilterExpression<Object> FILTER_EXPR_TWO = FilterExpression.of("filter-two");
    private static final FacetedSearchExpression<Object> FACETED_SEARCH_EXPR = FacetedSearchExpression.of(FACET_EXPR_ONE, singletonList(FILTER_EXPR_ONE));
    private static final ExpansionPath<Object> EXPANSION_PATH_ONE = ExpansionPath.of("expansion-one");
    private static final ExpansionPath<Object> EXPANSION_PATH_TWO = ExpansionPath.of("expansion-two");

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
                .containsExactlyElementsOf(singletonList(FACET_EXPR_ONE));
        assertThat(dsl.plusFacets(FACET_EXPR_TWO).facets())
                .containsExactlyElementsOf(asList(FACET_EXPR_ONE, FACET_EXPR_TWO));
    }

    @Test
    public void buildsQueryFilters() throws Exception {
        final TestableSearchDsl dsl = new TestableSearchDsl().withQueryFilters(singletonList(FILTER_EXPR_ONE));
        assertThat(dsl.queryFilters())
                .containsExactlyElementsOf(singletonList(FILTER_EXPR_ONE));
        assertThat(dsl.plusQueryFilters(singletonList(FILTER_EXPR_TWO)).queryFilters())
                .containsExactlyElementsOf(asList(FILTER_EXPR_ONE, FILTER_EXPR_TWO));
    }

    @Test
    public void buildsResultFilters() throws Exception {
        final TestableSearchDsl dsl = new TestableSearchDsl().withResultFilters(singletonList(FILTER_EXPR_ONE));
        assertThat(dsl.resultFilters())
                .containsExactlyElementsOf(singletonList(FILTER_EXPR_ONE));
        assertThat(dsl.plusResultFilters(singletonList(FILTER_EXPR_TWO)).resultFilters())
                .containsExactlyElementsOf(asList(FILTER_EXPR_ONE, FILTER_EXPR_TWO));
    }

    @Test
    public void buildsFacetFilters() throws Exception {
        final TestableSearchDsl dsl = new TestableSearchDsl().withFacetFilters(singletonList(FILTER_EXPR_ONE));
        assertThat(dsl.facetFilters())
                .containsExactlyElementsOf(singletonList(FILTER_EXPR_ONE));
        assertThat(dsl.plusFacetFilters(singletonList(FILTER_EXPR_TWO)).facetFilters())
                .containsExactlyElementsOf(asList(FILTER_EXPR_ONE, FILTER_EXPR_TWO));
    }

    @Test
    public void buildsFacetedSearch() throws Exception {
        final TestableSearchDsl dsl = new TestableSearchDsl().plusFacetedSearch(FACETED_SEARCH_EXPR);
        assertThat(dsl.facets())
                .containsExactlyElementsOf(singletonList(FACETED_SEARCH_EXPR.facetExpression()));
        assertThat(dsl.queryFilters()).isEmpty();
        assertThat(dsl.resultFilters())
                .containsExactlyElementsOf(dsl.facetFilters())
                .containsExactlyElementsOf(FACETED_SEARCH_EXPR.filterExpressions());
    }

    @Test
    public void buildsSort() throws Exception {
        final TestableSearchDsl dsl = new TestableSearchDsl().withSort(SORT_EXPR_ONE);
        assertThat(dsl.sort())
                .containsExactlyElementsOf(singletonList(SORT_EXPR_ONE));
        assertThat(dsl.plusSort(singletonList(SORT_EXPR_TWO)).sort())
                .containsExactlyElementsOf(asList(SORT_EXPR_ONE, SORT_EXPR_TWO));
    }

    @Test
    public void buildsExpansion() throws Exception {
        final TestableSearchDsl dsl = new TestableSearchDsl().withExpansionPaths(EXPANSION_PATH_ONE);
        assertThat(dsl.expansionPaths())
                .containsExactlyElementsOf(singletonList(EXPANSION_PATH_ONE));
        assertThat(dsl.plusExpansionPaths(singletonList(EXPANSION_PATH_TWO)).expansionPaths())
                .containsExactlyElementsOf(asList(EXPANSION_PATH_ONE, EXPANSION_PATH_TWO));
    }

}
