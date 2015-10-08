package io.sphere.sdk.products.search;

import io.sphere.sdk.products.*;
import io.sphere.sdk.search.*;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionSearchFacetedSearchIntegrationTest extends ProductProjectionSearchIntegrationTest {

    public static final FacetExpression<ProductProjection> COLOR_FACET_EXPR = model().allVariants().attribute().ofString(ATTR_NAME_COLOR).faceted().byAllTerms();
    public static final FacetExpression<ProductProjection> SIZE_FACET_EXPR = model().allVariants().attribute().ofString(ATTR_NAME_SIZE).faceted().byAllTerms();

    @Test
    public void facetedSearch() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusFacetedSearch(model -> model.allVariants().attribute().ofString(ATTR_NAME_SIZE).facetedSearch().allTerms())
                .plusFacetedSearch(model -> model.allVariants().attribute().ofString(ATTR_NAME_COLOR).facetedSearch().by("red"));
        testResult(search,
                ids -> assertThat(ids).containsOnly(product2.getId()),
                colors -> assertThat(colors).containsOnly(TermStats.of("blue", 2), TermStats.of("red", 1)),
                sizes -> assertThat(sizes).containsOnly(TermStats.of("36.0", 1)));
    }

    @Test
    public void resultsAndFacetsAreFiltered() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusResultFilters(model -> model.allVariants().attribute().ofString(ATTR_NAME_COLOR).filtered().by("red"))
                .plusFacetFilters(model -> model.allVariants().attribute().ofString(ATTR_NAME_COLOR).filtered().by("red"));
        testResultWithFacets(search,
                ids -> assertThat(ids).containsOnly(product2.getId()),
                colors -> assertThat(colors).containsOnly(TermStats.of("blue", 2), TermStats.of("red", 1)),
                sizes -> assertThat(sizes).containsOnly(TermStats.of("36.0", 1)));
    }

    @Test
    public void onlyFacetsAreFiltered() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusFacetFilters(model -> model.allVariants().attribute().ofString(ATTR_NAME_COLOR).filtered().by("red"));
        testResultWithFacets(search,
                ids -> assertThat(ids).containsOnly(product1.getId(), product2.getId(), product3.getId()),
                colors -> assertThat(colors).containsOnly(TermStats.of("blue", 2), TermStats.of("red", 1)),
                sizes -> assertThat(sizes).containsOnly(TermStats.of("36.0", 1)));
    }

    @Test
    public void onlyResultsAreFiltered() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusResultFilters(model -> model.allVariants().attribute().ofString(ATTR_NAME_COLOR).filtered().by("red"));
        testResultWithFacets(search,
                ids -> assertThat(ids).containsOnly(product2.getId()),
                colors -> assertThat(colors).containsOnly(TermStats.of("blue", 2), TermStats.of("red", 1)),
                sizes -> assertThat(sizes).containsOnly(TermStats.of("36.0", 1), TermStats.of("38.0", 1), TermStats.of("40.0", 1), TermStats.of("42.0", 1), TermStats.of("44.0", 1), TermStats.of("46.0", 1)));
    }

    @Test
    public void filterQueryFiltersBeforeFacetsAreCalculated() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(model -> model.allVariants().attribute().ofString(ATTR_NAME_COLOR).filtered().by("red"));
        testResultWithFacets(search,
                ids -> assertThat(ids).containsOnly(product2.getId()),
                colors -> assertThat(colors).containsOnly(TermStats.of("red", 1)),
                sizes -> assertThat(sizes).containsOnly(TermStats.of("36.0", 1)));
    }

    private static void testResultWithFacets(final ProductProjectionSearch search,
                                             final Consumer<List<String>> testFilter,
                                             final Consumer<List<TermStats>> testColors,
                                             final Consumer<List<TermStats>> testSizes) {
        final ProductProjectionSearch searchWithFacets = search.plusFacets(COLOR_FACET_EXPR).plusFacets(SIZE_FACET_EXPR);
        testResult(searchWithFacets, testFilter, testColors, testSizes);
    }

    private static void testResult(final ProductProjectionSearch search,
                                   final Consumer<List<String>> testFilter,
                                   final Consumer<List<TermStats>> testColors,
                                   final Consumer<List<TermStats>> testSizes) {
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        testFilter.accept(toIds(result.getResults()));
        final FacetExpression<ProductProjection> colorFacetExpr = model().allVariants().attribute().ofString(ATTR_NAME_COLOR).faceted().byAllTerms();
        final FacetExpression<ProductProjection> sizeFacetExpr = model().allVariants().attribute().ofString(ATTR_NAME_SIZE).faceted().byAllTerms();
        testColors.accept(result.getTermFacetResult(colorFacetExpr).getTerms());
        testSizes.accept(result.getTermFacetResult(sizeFacetExpr).getTerms());
    }
}
