package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;
import io.sphere.sdk.search.model.RangeStats;
import org.junit.Ignore;
import org.junit.Test;

import java.time.Duration;
import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class ProductProjectionSearchFacetsIntegrationTest extends ProductProjectionSearchIntegrationTest {

    public static final ProductDataFacetSearchModel PRODUCT_MODEL = ProductProjectionSearchModel.of().facet();

    @Ignore
    @Test
    public void responseContainsRangeFacetsForAttributes() throws Exception {
        final RangeFacetExpression<ProductProjection> facetExpr = PRODUCT_MODEL.allVariants().price().centAmount().onlyGreaterThanOrEqualTo(0L);
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        testResult(search, result ->
                assertThat(result.getFacetResult(facetExpr).getRanges().get(0).getCount()).isGreaterThan(0));
    }

    @Ignore
    @Test
    public void responseContainsTermFacetsForAttributes() throws Exception {
        final TermFacetExpression<ProductProjection> facetExpr = PRODUCT_MODEL.allVariants().attribute().ofString(ATTR_NAME_COLOR).allTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        testResult(search, result ->
                assertThat(result.getFacetResult(facetExpr).getTerms()).containsOnly(TermStats.of("blue", 2L), TermStats.of("red", 1L)));
    }

    @Ignore
    @Test
    public void termFacetsAreParsed() throws Exception {
        final TermFacetExpression<ProductProjection> facetExpr = PRODUCT_MODEL.allVariants().attribute().ofString(ATTR_NAME_COLOR).allTerms();
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        testResult(search, result -> {
            final TermFacetResult termFacetResult = result.getFacetResult(facetExpr);
            assertThat(termFacetResult.getMissing()).isGreaterThanOrEqualTo(3);
            assertThat(termFacetResult.getTotal()).isEqualTo(3);
            assertThat(termFacetResult.getOther()).isEqualTo(0);
            assertThat(termFacetResult.getTerms()).isEqualTo(asList(TermStats.of("blue", 2L), TermStats.of("red", 1L)));
        });
    }

    @Ignore
    @Test
    public void rangeFacetsAreParsed() throws Exception {
        final RangeFacetExpression<ProductProjection> facetExpr = PRODUCT_MODEL.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).onlyGreaterThanOrEqualTo(ZERO);
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        testResult(search, result -> {
            final RangeStats rangeStats = result.getFacetResult(facetExpr).getRanges().get(0);
            assertThat(rangeStats.getLowerEndpoint()).isEqualTo("0.0");
            assertThat(rangeStats.getUpperEndpoint()).isNull();
            assertThat(rangeStats.getCount()).isEqualTo(6L);
            assertThat(rangeStats.getMin()).isEqualTo("36.0");
            assertThat(rangeStats.getMax()).isEqualTo("46.0");
            assertThat(rangeStats.getSum()).isEqualTo("246.0");
            assertThat(rangeStats.getMean()).isEqualTo(41.0);
        });
    }

    @Ignore
    @Test
    public void filteredFacetsAreParsed() throws Exception {
        final FilteredFacetExpression<ProductProjection> facetExpr = PRODUCT_MODEL.allVariants().attribute().ofString(ATTR_NAME_COLOR).onlyTerm("blue");
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        testResult(search, result -> {
            final FilteredFacetResult filteredFacetResult = result.getFacetResult(facetExpr);
            assertThat(filteredFacetResult.getCount()).isEqualTo(2);
        });
    }

    @Ignore
    @Test
    public void simpleFacetsAreParsed() throws Exception {
        final TermFacetExpression<ProductProjection> facetExpr = TermFacetExpression.of("variants.attributes." + ATTR_NAME_COLOR);
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        testResult(search, result -> {
            final TermFacetResult termFacetResult = result.getFacetResult(facetExpr);
            assertThat(termFacetResult.getMissing()).isGreaterThanOrEqualTo(3);
            assertThat(termFacetResult.getTotal()).isEqualTo(3);
            assertThat(termFacetResult.getOther()).isEqualTo(0);
            assertThat(termFacetResult.getTerms()).isEqualTo(asList(TermStats.of("blue", 2L), TermStats.of("red", 1L)));
        });
    }

    @Ignore
    @Test
    public void termFacetsSupportsAlias() throws Exception {
        final String allFacetAlias = "my-facet";
        final String blueFacetAlias = "my-blue-facet";
        final TermFacetExpression<ProductProjection> allFacetExpr = PRODUCT_MODEL.allVariants().attribute().ofString(ATTR_NAME_COLOR).withAlias(allFacetAlias).allTerms();
        final FilteredFacetExpression<ProductProjection> blueFacetExpr = PRODUCT_MODEL.allVariants().attribute().ofString(ATTR_NAME_COLOR).withAlias(blueFacetAlias).onlyTerm("blue");
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusFacets(allFacetExpr)
                .plusFacets(blueFacetExpr);
        testResult(search, result -> {
            final TermFacetResult allFacetResult = result.getFacetResult(allFacetExpr);
            final FilteredFacetResult blueFacetResult = result.getFilteredFacetResult(blueFacetAlias);
            assertThat(allFacetExpr.resultPath()).isEqualTo(allFacetAlias);
            assertThat(blueFacetExpr.resultPath()).isEqualTo(blueFacetAlias);
            assertThat(allFacetResult.getTerms()).isEqualTo(asList(TermStats.of("blue", 2L), TermStats.of("red", 1L)));
            assertThat(blueFacetResult.getCount()).isEqualTo(2);
        });
    }

    @Ignore
    @Test
    public void rangeFacetsSupportsAlias() throws Exception {
        final String alias = "my-facet";
        final RangeFacetExpression<ProductProjection> facetExpr = PRODUCT_MODEL.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).withAlias(alias).onlyGreaterThanOrEqualTo(ZERO);
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        testResult(search, result -> {
            assertThat(facetExpr.resultPath()).isEqualTo(alias);
            final RangeStats rangeStats = result.getFacetResult(facetExpr).getRanges().get(0);
            assertThat(rangeStats.getLowerEndpoint()).isEqualTo("0.0");
            assertThat(rangeStats.getUpperEndpoint()).isNull();
            assertThat(rangeStats.getCount()).isEqualTo(6L);
            assertThat(rangeStats.getMin()).isEqualTo("36.0");
            assertThat(rangeStats.getMax()).isEqualTo("46.0");
            assertThat(rangeStats.getSum()).isEqualTo("246.0");
            assertThat(rangeStats.getMean()).isEqualTo(41.0);
        });
    }

    @Ignore
    @Test
    public void filteredFacetsSupportsAlias() throws Exception {
        final String alias = "my-facet";
        final FilteredFacetExpression<ProductProjection> facetExpr = PRODUCT_MODEL.allVariants().attribute().ofString(ATTR_NAME_COLOR).withAlias(alias).onlyTerm("blue");
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusFacets(facetExpr);
        testResult(search, result -> {
            assertThat(facetExpr.resultPath()).isEqualTo(alias);
            assertThat(result.getFacetResult(facetExpr).getCount()).isEqualTo(2);
        });
    }


    private static void testResult(final ProductProjectionSearch search, final Consumer<PagedSearchResult<ProductProjection>> test) {
        assertEventually(Duration.ofSeconds(45), Duration.ofMillis(200), () -> test.accept(executeSearch(search)));
    }
}
