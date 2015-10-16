package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;
import io.sphere.sdk.search.model.RangeStats;
import org.junit.Test;

import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionSearchFacetsIntegrationTest extends ProductProjectionSearchIntegrationTest {

    public static final ProductDataFacetSearchModel FACET = ProductProjectionSearchModel.of().facet();

    @Test
    public void responseContainsRangeFacetsForAttributes() throws Exception {
        final RangeFacetExpression<ProductProjection> facetExpr = FACET.allVariants().price().centAmount().onlyGreaterThanOrEqualTo(0L);
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(result.getRangeFacetResult(facetExpr).getRanges().get(0).getCount()).isGreaterThan(0);
    }

    @Test
    public void responseContainsTermFacetsForAttributes() throws Exception {
        final TermFacetExpression<ProductProjection> facetExpr = FACET.allVariants().attribute().ofString(ATTR_NAME_COLOR).allTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(result.getTermFacetResult(facetExpr).getTerms()).containsOnly(TermStats.of("blue", 2), TermStats.of("red", 1));
    }

    @Test
    public void termFacetsAreParsed() throws Exception {
        final TermFacetExpression<ProductProjection> facetExpr = FACET.allVariants().attribute().ofString(ATTR_NAME_COLOR).allTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        final TermFacetResult termFacetResult = result.getTermFacetResult(facetExpr);
        assertThat(termFacetResult.getMissing()).isGreaterThanOrEqualTo(3);
        assertThat(termFacetResult.getTotal()).isEqualTo(3);
        assertThat(termFacetResult.getOther()).isEqualTo(0);
        assertThat(termFacetResult.getTerms()).isEqualTo(asList(TermStats.of("blue", 2), TermStats.of("red", 1)));
    }

    @Test
    public void rangeFacetsAreParsed() throws Exception {
        final RangeFacetExpression<ProductProjection> facetExpr = FACET.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).onlyGreaterThanOrEqualTo(ZERO);
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        final RangeStats rangeStats = result.getRangeFacetResult(facetExpr).getRanges().get(0);
        assertThat(rangeStats.getLowerEndpoint()).isEqualTo("0.0");
        assertThat(rangeStats.getUpperEndpoint()).isNull();
        assertThat(rangeStats.getCount()).isEqualTo(6L);
        assertThat(rangeStats.getMin()).isEqualTo("36.0");
        assertThat(rangeStats.getMax()).isEqualTo("46.0");
        assertThat(rangeStats.getSum()).isEqualTo("246.0");
        assertThat(rangeStats.getMean()).isEqualTo(41.0);
    }

    @Test
    public void filteredFacetsAreParsed() throws Exception {
        final FilteredFacetExpression<ProductProjection> facetExpr = FACET.allVariants().attribute().ofString(ATTR_NAME_COLOR).onlyTerm("blue");
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        final FilteredFacetResult filteredFacetResult = result.getFilteredFacetResult(facetExpr);
        assertThat(filteredFacetResult.getCount()).isEqualTo(2);
    }

    @Test
    public void simpleFacetsAreParsed() throws Exception {
        final TermFacetExpression<ProductProjection> facetExpr = TermFacetExpression.of("variants.attributes." + ATTR_NAME_COLOR);
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final TermFacetResult termFacetResult = executeSearch(search).getTermFacetResult(facetExpr);
        assertThat(termFacetResult.getMissing()).isGreaterThanOrEqualTo(3);
        assertThat(termFacetResult.getTotal()).isEqualTo(3);
        assertThat(termFacetResult.getOther()).isEqualTo(0);
        assertThat(termFacetResult.getTerms()).isEqualTo(asList(TermStats.of("blue", 2), TermStats.of("red", 1)));
    }

    @Test
    public void termFacetsSupportsAlias() throws Exception {
        final String allFacetAlias = "my-facet";
        final String blueFacetAlias = "my-blue-facet";
        final TermFacetExpression<ProductProjection> allFacetExpr = FACET.allVariants().attribute().ofString(ATTR_NAME_COLOR).withAlias(allFacetAlias).allTerms();
        final FilteredFacetExpression<ProductProjection> blueFacetExpr = FACET.allVariants().attribute().ofString(ATTR_NAME_COLOR).withAlias(blueFacetAlias).onlyTerm("blue");
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusFacets(allFacetExpr)
                .plusFacets(blueFacetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        final TermFacetResult allFacetResult = result.getTermFacetResult(allFacetExpr);
        final FilteredFacetResult blueFacetResult = result.getFilteredFacetResult(blueFacetAlias);
        assertThat(allFacetExpr.resultPath()).isEqualTo(allFacetAlias);
        assertThat(blueFacetExpr.resultPath()).isEqualTo(blueFacetAlias);
        assertThat(allFacetResult.getTerms()).isEqualTo(asList(TermStats.of("blue", 2), TermStats.of("red", 1)));
        assertThat(blueFacetResult.getCount()).isEqualTo(2);
    }

    @Test
    public void rangeFacetsSupportsAlias() throws Exception {
        final String alias = "my-facet";
        final RangeFacetExpression<ProductProjection> facetExpr = FACET.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).withAlias(alias).onlyGreaterThanOrEqualTo(ZERO);
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(facetExpr.resultPath()).isEqualTo(alias);
        final RangeStats rangeStats = result.getRangeFacetResult(facetExpr).getRanges().get(0);
        assertThat(rangeStats.getLowerEndpoint()).isEqualTo("0.0");
        assertThat(rangeStats.getUpperEndpoint()).isNull();
        assertThat(rangeStats.getCount()).isEqualTo(6L);
        assertThat(rangeStats.getMin()).isEqualTo("36.0");
        assertThat(rangeStats.getMax()).isEqualTo("46.0");
        assertThat(rangeStats.getSum()).isEqualTo("246.0");
        assertThat(rangeStats.getMean()).isEqualTo(41.0);
    }

    @Test
    public void filteredFacetsSupportsAlias() throws Exception {
        final String alias = "my-facet";
        final FilteredFacetExpression<ProductProjection> facetExpr = FACET.allVariants().attribute().ofString(ATTR_NAME_COLOR).withAlias(alias).onlyTerm("blue");
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(facetExpr.resultPath()).isEqualTo(alias);
        assertThat(result.getFilteredFacetResult(facetExpr).getCount()).isEqualTo(2);
    }
}
