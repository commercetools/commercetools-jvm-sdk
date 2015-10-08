package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionSearchSortIntegrationTest extends ProductProjectionSearchIntegrationTest {

    @Test
    public void sortByAttributeAscending() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withSort(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).sorted().byAsc());
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsExactly(product2.getId(), product1.getId(), product3.getId());
    }

    @Test
    public void sortByAttributeDescending() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withSort(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).sorted().byDesc());
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsExactly(product1.getId(), product2.getId(), product3.getId());
    }

    @Test
    public void sortWithAdditionalParameterByAttributeAscending() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withSort(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).sorted().byAscWithMax());
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsExactly(product3.getId(), product2.getId(), product1.getId());
    }

    @Test
    public void sortWithAdditionalParameterByAttributeDescending() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withSort(model -> model.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).sorted().byDescWithMin());
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsExactly(product3.getId(), product1.getId(), product2.getId());
    }

    @Test
    public void sortWithSimpleExpression() {
        final SortExpression<ProductProjection> sort = SortExpression.of("variants.attributes." + ATTR_NAME_SIZE + " asc.max");
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withSort(sort);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsExactly(product3.getId(), product2.getId(), product1.getId());
    }

    @Test
    public void sortByMultipleAttributes() throws Exception {
        final ProductProjectionSearch singleSortedRequest = ProductProjectionSearch.ofStaged()
                .plusSort(model -> model.allVariants().attribute().ofString(ATTR_NAME_COLOR).sorted().byAsc());
        final PagedSearchResult<ProductProjection> resultNameAsc = executeSearch(singleSortedRequest
                .plusSort(model -> model.name().locale(ENGLISH).sorted().byAsc()));
        final PagedSearchResult<ProductProjection> resultNameDesc = executeSearch(singleSortedRequest
                .plusSort(model -> model.name().locale(ENGLISH).sorted().byDesc()));
        assertThat(resultsToIds(resultNameAsc)).containsExactly(product3.getId(), product1.getId(), product2.getId());
        assertThat(resultsToIds(resultNameDesc)).containsExactly(product1.getId(), product3.getId(), product2.getId());
    }
}
