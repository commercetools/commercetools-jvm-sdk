package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.search.SortExpression;
import org.junit.Ignore;
import org.junit.Test;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;

import static io.sphere.sdk.test.SphereTestUtils.ENGLISH;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class ProductProjectionSearchSortIntegrationTest extends ProductProjectionSearchIntegrationTest {

    @Ignore
    @Test
    public void sortByAttributeAscending() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withSort(productModel -> productModel.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).asc());
        testResultIds(search, resultIds ->
                assertThat(resultIds).containsExactly(product2.getId(), product1.getId(), product3.getId()));
    }

    @Ignore
    @Test
    public void sortByAttributeDescending() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withSort(productModel -> productModel.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).desc());
        testResultIds(search, resultIds ->
                assertThat(resultIds).containsExactly(product1.getId(), product2.getId(), product3.getId()));
    }

    @Ignore
    @Test
    public void sortWithAdditionalParameterByAttributeAscending() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withSort(productModel -> productModel.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).ascWithMaxValue());
        testResultIds(search, resultIds ->
                assertThat(resultIds).containsExactly(product3.getId(), product2.getId(), product1.getId()));
    }

    @Ignore
    @Test
    public void sortWithAdditionalParameterByAttributeDescending() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withSort(productModel -> productModel.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).descWithMinValue());
        testResultIds(search, resultIds ->
                assertThat(resultIds).containsExactly(product3.getId(), product1.getId(), product2.getId()));
    }

    @Ignore
    @Test
    public void sortWithSimpleExpression() {
        final SortExpression<ProductProjection> sort = SortExpression.of("variants.attributes." + ATTR_NAME_SIZE + " asc.max");
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withSort(sort);
        testResultIds(search, resultIds ->
                assertThat(resultIds).containsExactly(product3.getId(), product2.getId(), product1.getId()));
    }

    @Ignore
    @Test
    public void sortByMultipleAttributes() throws Exception {
        final ProductProjectionSearch singleSortedRequest = ProductProjectionSearch.ofStaged()
                .plusSort(productModel -> productModel.allVariants().attribute().ofString(ATTR_NAME_COLOR).asc());
        final ProductProjectionSearch searchWithNameAsc = singleSortedRequest
                .plusSort(productModel -> productModel.name().locale(ENGLISH).asc());
        testResultIds(searchWithNameAsc, resultIds ->
                assertThat(resultIds).containsExactly(product3.getId(), product1.getId(), product2.getId()));
        final ProductProjectionSearch searchWithNameDesc = singleSortedRequest
                .plusSort(productModel -> productModel.name().locale(ENGLISH).desc());
        testResultIds(searchWithNameDesc, resultIds ->
                assertThat(resultIds).containsExactly(product1.getId(), product3.getId(), product2.getId()));
    }

    private static void testResultIds(final ProductProjectionSearch search, final Consumer<List<String>> test) {
        assertEventually(Duration.ofSeconds(45), Duration.ofMillis(200), () -> {
            final PagedSearchResult<ProductProjection> result = executeSearch(search);
            test.accept(resultsToIds(result));
        });
    }
}
