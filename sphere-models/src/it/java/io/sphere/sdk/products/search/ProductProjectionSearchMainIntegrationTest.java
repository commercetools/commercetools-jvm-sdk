package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.PagedSearchResult;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.ENGLISH;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionSearchMainIntegrationTest extends ProductProjectionSearchIntegrationTest {

    @Test
    public void searchByTextInACertainLanguage() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withText(ENGLISH, "shoe");
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsOnly(product1.getId());
    }

    @Test
    public void resultsArePaginated() throws Exception {
        final PagedSearchResult<ProductProjection> result = executeSearch(ProductProjectionSearch.ofStaged()
                .plusQueryFilters(model -> model().allVariants().attribute().ofString(ATTR_NAME_COLOR).filtered().byAny(asList("blue", "red")))
                .withSort(model -> model().name().locale(ENGLISH).sorted().byDesc())
                .withOffset(1L)
                .withLimit(1L));
        assertThat(resultsToIds(result)).containsOnly(product2.getId());
    }

    @Test
    public void paginationExample() {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withOffset(50L)
                .withLimit(25L);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(result.getOffset()).isEqualTo(50);
        assertThat(result.getResults().size()).isEqualTo(min(25, max(result.getTotal() - 50, 0)));
    }

    @Test
    public void allowsExpansion() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withLimit(1);
        assertThat(executeSearch(search).getResults().get(0).getProductType().getObj()).isNull();
        final PagedSearchResult<ProductProjection> result = executeSearch(search.withExpansionPaths(model -> model.productType()));
        assertThat(result.getResults().get(0).getProductType().getObj()).isEqualTo(productType);
    }

}
