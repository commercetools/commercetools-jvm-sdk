package io.sphere.sdk.products.search;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.PagedSearchResult;
import org.junit.Test;

import java.util.List;
import java.util.function.Predicate;

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
                .plusQueryFilters(filter -> filter.allVariants().attribute().ofString(ATTR_NAME_COLOR).byAny(asList("blue", "red")))
                .withSort(sort -> sort.name().locale(ENGLISH).byDesc())
                .withOffset(1L)
                .withLimit(1L));
        assertThat(resultsToIds(result)).containsOnly(product2.getId());
    }

    @Test
    public void paginationExample() {
        final long offset = 10;
        final long limit = 25;
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withOffset(offset)
                .withLimit(limit);
        final PagedSearchResult<ProductProjection> result = executeSearch(search);
        final long remainingProducts = max(result.getTotal() - offset, 0);
        final long expectedProducts = min(limit, remainingProducts);
        assertThat(result.size()).as("size").isEqualTo(expectedProducts);
        assertThat(result.getOffset()).as("offset").isEqualTo(offset);
    }

    @Test
    public void allowsExpansion() throws Exception {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged().withLimit(1L);
        assertThat(executeSearch(search).getResults().get(0).getProductType().getObj()).isNull();
        final PagedSearchResult<ProductProjection> result = executeSearch(search.withExpansionPaths(model -> model.productType()));
        assertThat(result.getResults().get(0).getProductType().getObj()).isEqualTo(productType);
    }

    @Test
    public void fuzzySearch() {
        final ProductProjectionSearch search = ProductProjectionSearch.ofStaged()
                .withText(ENGLISH, "short")
                .withQueryFilters(filter -> filter.productType().id().by(productType.getId()));
        assertThat(execute(search).getResults()).matches(containsIdentifiable(product2).negate(), "not included");
        assertThat(execute(search.withFuzzy(true)).getResults()).matches(containsIdentifiable(product2), "included");
    }

    private <T> Predicate<List<? extends Identifiable<T>>> containsIdentifiable(final Identifiable<T> identifiable) {
        return list -> list.stream().anyMatch(element -> element.getId().equals(identifiable.getId()));
    }

}
