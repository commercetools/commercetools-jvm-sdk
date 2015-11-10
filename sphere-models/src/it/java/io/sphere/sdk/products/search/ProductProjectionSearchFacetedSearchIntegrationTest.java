package io.sphere.sdk.products.search;

import io.sphere.sdk.products.*;
import io.sphere.sdk.search.*;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionSearchFacetedSearchIntegrationTest extends ProductProjectionSearchIntegrationTest {

    private static final ProductProjectionFacetAndFilterSearchModel FACETED_SEARCH = ProductProjectionSearchModel.of().facetedSearch();
    private static final TermFacetAndFilterExpression<ProductProjection> COLOR_FACETED_SEARCH = FACETED_SEARCH.allVariants().attribute().ofString(ATTR_NAME_COLOR).by("red");
    private static final TermFacetAndFilterExpression<ProductProjection> SIZE_FACETED_SEARCH = FACETED_SEARCH.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).byAny(emptyList());

    @Test
    public void facetedSearch() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusFacetedSearch(SIZE_FACETED_SEARCH)
                .plusFacetedSearch(COLOR_FACETED_SEARCH);
        testResult(search,
                ids -> assertThat(ids).containsOnly(product2.getId()),
                colors -> assertThat(colors).containsOnly(TermStats.of("blue", 2), TermStats.of("red", 1)),
                sizes -> assertThat(sizes).containsOnly(TermStats.of("36.0", 1)));
    }

    @Test
    public void resultsAndFacetsAreFiltered() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusFacets(SIZE_FACETED_SEARCH.facetExpression())
                .plusFacets(COLOR_FACETED_SEARCH.facetExpression())
                .plusResultFilters(COLOR_FACETED_SEARCH.filterExpressions())
                .plusFacetFilters(COLOR_FACETED_SEARCH.filterExpressions());
        testResult(search,
                ids -> assertThat(ids).containsOnly(product2.getId()),
                colors -> assertThat(colors).containsOnly(TermStats.of("blue", 2), TermStats.of("red", 1)),
                sizes -> assertThat(sizes).containsOnly(TermStats.of("36.0", 1)));
    }

    @Test
    public void onlyFacetsAreFiltered() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusFacets(SIZE_FACETED_SEARCH.facetExpression())
                .plusFacets(COLOR_FACETED_SEARCH.facetExpression())
                .plusFacetFilters(COLOR_FACETED_SEARCH.filterExpressions());
        testResult(search,
                ids -> assertThat(ids).containsOnly(product1.getId(), product2.getId(), product3.getId()),
                colors -> assertThat(colors).containsOnly(TermStats.of("blue", 2), TermStats.of("red", 1)),
                sizes -> assertThat(sizes).containsOnly(TermStats.of("36.0", 1)));
    }

    @Test
    public void onlyResultsAreFiltered() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusFacets(SIZE_FACETED_SEARCH.facetExpression())
                .plusFacets(COLOR_FACETED_SEARCH.facetExpression())
                .plusResultFilters(COLOR_FACETED_SEARCH.filterExpressions());
        testResult(search,
                ids -> assertThat(ids).containsOnly(product2.getId()),
                colors -> assertThat(colors).containsOnly(TermStats.of("blue", 2), TermStats.of("red", 1)),
                sizes -> assertThat(sizes).containsOnly(TermStats.of("36.0", 1), TermStats.of("38.0", 1), TermStats.of("40.0", 1), TermStats.of("42.0", 1), TermStats.of("44.0", 1), TermStats.of("46.0", 1)));
    }

    @Test
    public void filterQueryFiltersBeforeFacetsAreCalculated() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusFacets(SIZE_FACETED_SEARCH.facetExpression())
                .plusFacets(COLOR_FACETED_SEARCH.facetExpression())
                .plusQueryFilters(COLOR_FACETED_SEARCH.filterExpressions());
        testResult(search,
                ids -> assertThat(ids).containsOnly(product2.getId()),
                colors -> assertThat(colors).containsOnly(TermStats.of("red", 1)),
                sizes -> assertThat(sizes).containsOnly(TermStats.of("36.0", 1)));
    }

    private static void testResult(final ProductProjectionSearch search,
                                   final Consumer<List<String>> testFilter,
                                   final Consumer<List<TermStats>> testColors,
                                   final Consumer<List<TermStats>> testSizes) {
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        testFilter.accept(toIds(result.getResults()));
        testColors.accept(result.getTermFacetResult(COLOR_FACETED_SEARCH.facetExpression()).getTerms());
        testSizes.accept(result.getTermFacetResult(SIZE_FACETED_SEARCH.facetExpression()).getTerms());
    }
}