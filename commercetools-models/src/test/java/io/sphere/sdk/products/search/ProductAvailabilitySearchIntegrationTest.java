package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.PagedSearchResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.products.ProductFixtures.withProductOfStock;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductAvailabilitySearchIntegrationTest extends IntegrationTest {
    @Test
    public void searchForIsOnStock() {
        withProductOfStock(client(), 2, product -> {
            final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                    .plusQueryFilters(m -> m.id().is(product.getId()))
                    .plusQueryFilters(m -> m.allVariants().availability().isOnStock().is(true));
            assertEventually(() -> {
                final PagedSearchResult<ProductProjection> res = client().executeBlocking(request);
                assertThat(res.getResults()).hasSize(1);
            });
        });
    }

    @Test
    public void searchForIsNotOnStock() {
        withProductOfStock(client(), 0, product -> {
            final ProductProjectionSearch request = ProductProjectionSearch.ofStaged()
                    .plusQueryFilters(m -> m.id().is(product.getId()))
                    .plusQueryFilters(m -> m.allVariants().availability().isOnStock().is(false));
            assertEventually(() -> {
                final PagedSearchResult<ProductProjection> res = client().executeBlocking(request);
                assertThat(res.getResults()).hasSize(0);
            });
        });
    }
}
