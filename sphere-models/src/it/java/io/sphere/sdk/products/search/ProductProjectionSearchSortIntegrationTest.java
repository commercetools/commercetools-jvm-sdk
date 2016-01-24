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
                .withSort(productModel -> productModel.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).asc());
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsExactly(product2.getId(), product1.getId(), product3.getId());
    }

    @Test
    public void sortByAttributeDescending() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withSort(productModel -> productModel.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).desc());
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsExactly(product1.getId(), product2.getId(), product3.getId());
    }

    @Test
    public void sortWithAdditionalParameterByAttributeAscending() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withSort(productModel -> productModel.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).ascWithMaxValue());
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsExactly(product3.getId(), product2.getId(), product1.getId());
    }

    @Test
    public void sortWithAdditionalParameterByAttributeDescending() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withSort(productModel -> productModel.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).descWithMinValue());
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
                .plusSort(productModel -> productModel.allVariants().attribute().ofString(ATTR_NAME_COLOR).asc());
        final PagedSearchResult<ProductProjection> resultNameAsc = executeSearch(singleSortedRequest
                .plusSort(productModel -> productModel.name().locale(ENGLISH).asc()));
        final PagedSearchResult<ProductProjection> resultNameDesc = executeSearch(singleSortedRequest
                .plusSort(productModel -> productModel.name().locale(ENGLISH).desc()));
        assertThat(resultsToIds(resultNameAsc)).containsExactly(product3.getId(), product1.getId(), product2.getId());
        assertThat(resultsToIds(resultNameDesc)).containsExactly(product1.getId(), product3.getId(), product2.getId());
    }
}
