package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.PagedSearchResult;
import org.junit.Ignore;
import org.junit.Test;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static io.sphere.sdk.search.model.FilterRange.atLeast;
import static io.sphere.sdk.search.model.FilterRange.atMost;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@Ignore
public class ProductProjectionSearchFiltersIntegrationTest extends ProductProjectionSearchIntegrationTest {

    @Ignore
    @Test
    public void filtersByTerm() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(productModel -> productModel.allVariants().attribute().ofString(ATTR_NAME_COLOR).is("red"));
        testResultIds(search, resultIds ->
                assertThat(resultIds).containsOnly(product2.getId()));
    }

    @Ignore
    @Test
    public void filtersByAnyTerm() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(productModel -> productModel.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).isIn(asList(valueOf(36), valueOf(38))));
        testResultIds(search, resultIds ->
                assertThat(resultIds).containsOnly(product1.getId(), product2.getId()));
    }

    @Ignore
    @Test
    public void filtersByRange() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(productModel -> productModel.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).isGreaterThanOrEqualTo(valueOf(44)));
        testResultIds(search, resultIds ->
                assertThat(resultIds).containsOnly(product1.getId(), product2.getId()));
    }

    @Ignore
    @Test
    public void filtersByAnyRange() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(productModel -> productModel.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).isBetweenAny(asList(atLeast(valueOf(46)), atMost(valueOf(36)))));
        testResultIds(search, resultIds ->
                assertThat(resultIds).containsOnly(product1.getId(), product2.getId()));
    }

    @Ignore
    @Test
    public void filtersByAllRanges() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(productModel -> productModel.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).isBetweenAll(asList(atLeast(valueOf(39)), atMost(valueOf(43)))));
        testResultIds(search, resultIds ->
                assertThat(resultIds).containsOnly(product3.getId()));
    }

    @Ignore
    @Test
    public void simpleFilterByRange() throws Exception {
        final FilterExpression<ProductProjection> filterExpr = FilterExpression.of("variants.attributes." + ATTR_NAME_SIZE + ":range(44 to *)");
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusQueryFilters(singletonList(filterExpr));
        testResultIds(search, resultIds ->
                assertThat(resultIds).containsOnly(product1.getId(), product2.getId()));
    }

    @Ignore
    @Test
    public void filterByValueAsString() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(productModel -> productModel.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).containsAnyAsString(asList("36", "38")));
        testResultIds(search, resultIds ->
                assertThat(resultIds).containsOnly(product1.getId(), product2.getId()));
    }

    @Ignore
    @Test
    public void filterByEvilCharacterWord() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(productModel -> productModel.allVariants().attribute().ofString(ATTR_NAME_EVIL).is(EVIL_CHARACTER_WORD));
        assertEventually(Duration.ofSeconds(60), Duration.ofMillis(200), () -> {
            final PagedSearchResult<ProductProjection> result = executeEvilSearch(search);
            assertThat(result.getTotal()).as("total").isEqualTo(1);
        });
    }

    @Ignore
    @Test
    public void filterBySku() {
        final List<String> skus = Stream.of(product1, product2)
                .map(product -> product.getMasterData().getStaged().getMasterVariant().getSku())
                .collect(toList());
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(m -> m.allVariants().sku().isIn(skus));
        assertEventually(Duration.ofSeconds(45), Duration.ofMillis(200), () -> {
            assertThat(client().executeBlocking(search).getResults())
            .extracting(m -> m.getMasterVariant().getSku())
            .containsOnly(skus.get(0), skus.get(1));
        });
    }

    @Ignore
    @Test
    public void filterByProductVariantKey() {
        final List<String> keys = Stream.of(product1, product2)
                .map(product -> product.getMasterData().getStaged().getMasterVariant().getKey())
                .collect(toList());
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(m -> m.allVariants().key().isIn(keys));
        assertEventually(Duration.ofSeconds(45), Duration.ofMillis(200), () -> {
            final List<ProductProjection> results = client().executeBlocking(search).getResults();
            assertThat(results)
            .extracting(m -> m.getMasterVariant().getKey())
            .containsOnly(keys.get(0), keys.get(1));
        });
    }

    @Ignore
    @Test
    public void filterByProductKey() {
        final List<String> keys = Stream.of(product1, product2)
                .map(product -> product.getKey())
                .collect(toList());
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(m -> m.key().isIn(keys));
        assertEventually(Duration.ofSeconds(45), Duration.ofMillis(200), () -> {
            final List<ProductProjection> results = client().executeBlocking(search).getResults();
            assertThat(results)
            .extracting(m -> m.getKey())
            .containsOnly(keys.get(0), keys.get(1));
        });
    }

    private static void testResultIds(final ProductProjectionSearch search, final Consumer<List<String>> test) {
        assertEventually(Duration.ofSeconds(45), Duration.ofMillis(200), () -> {
            final PagedSearchResult<ProductProjection> result = executeSearch(search);
            test.accept(resultsToIds(result));
        });
    }
}
