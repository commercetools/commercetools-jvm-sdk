package io.sphere.sdk.products.search;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;
import org.junit.Test;

import java.util.List;
import java.util.stream.Stream;

import static io.sphere.sdk.search.model.FilterRange.atLeast;
import static io.sphere.sdk.search.model.FilterRange.atMost;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionSearchFiltersIntegrationTest extends ProductProjectionSearchIntegrationTest {

    @Test
    public void filtersByTerm() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(productModel -> productModel.allVariants().attribute().ofString(ATTR_NAME_COLOR).is("red"));
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsOnly(product2.getId());
    }

    @Test
    public void filtersByAnyTerm() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(productModel -> productModel.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).isIn(asList(valueOf(36), valueOf(38))));
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsOnly(product1.getId(), product2.getId());
    }

    @Test
    public void filtersByRange() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(productModel -> productModel.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).isGreaterThanOrEqualTo(valueOf(44)));
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsOnly(product1.getId(), product2.getId());
    }

    @Test
    public void filtersByAnyRange() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(productModel -> productModel.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).isBetweenAny(asList(atLeast(valueOf(46)), atMost(valueOf(36)))));
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsOnly(product1.getId(), product2.getId());
    }

    @Test
    public void filtersByAllRanges() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(productModel -> productModel.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).isBetweenAll(asList(atLeast(valueOf(39)), atMost(valueOf(43)))));
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsOnly(product3.getId());
    }

    @Test
    public void simpleFilterByRange() throws Exception {
        final FilterExpression<ProductProjection> filterExpr = FilterExpression.of("variants.attributes." + ATTR_NAME_SIZE + ":range(44 to *)");
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().plusQueryFilters(singletonList(filterExpr));
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        assertThat(resultsToIds(result)).containsOnly(product1.getId(), product2.getId());
    }

    @Test
    public void filterByEvilCharacterWord() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(productModel -> productModel.allVariants().attribute().ofString(ATTR_NAME_EVIL).is(EVIL_CHARACTER_WORD));
        assertEventually(() -> {
            final PagedSearchResult<ProductProjection> result = executeEvilSearch(search);
            assertThat(result.getTotal()).isEqualTo(1);
        });
    }

    @Test
    public void filterByValueAsString() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(productModel -> productModel.allVariants().attribute().ofNumber(ATTR_NAME_SIZE).containsAnyAsString(asList("36", "38")));
        assertEventually(() -> {
            final PagedSearchResult<ProductProjection> result = executeSearch(search);
            assertThat(resultsToIds(result)).containsOnly(product1.getId(), product2.getId());
        });
    }

    @Test
    public void filterBySku() {
        final List<String> skus = Stream.of(product1, product2)
                .map(product -> product.getMasterData().getStaged().getMasterVariant().getSku())
                .collect(toList());
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .plusQueryFilters(m -> m.allVariants().sku().isIn(skus));
        assertEventually(() -> {
            assertThat(client().executeBlocking(search).getResults())
            .extracting(m -> m.getMasterVariant().getSku())
            .containsOnly(skus.get(0), skus.get(1));
        });
    }
}
