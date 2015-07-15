package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.products.ProductProjectionType.STAGED;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionByIdFetchTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withProduct(client(), "getProductProjectionById", product -> {
            final ProductProjectionType projectionType = STAGED;
            final String productId = product.getId();
            final ProductProjectionByIdFetch sphereRequest = ProductProjectionByIdFetch.of(productId, projectionType);
            final ProductProjection productProjection = execute(sphereRequest);
            final String fetchedProjectionId = productProjection.getId();
            assertThat(fetchedProjectionId).isEqualTo(productId);
            assertThat(productProjection.getCategories()).isEqualTo(product.getMasterData().get(projectionType).get().getCategories());
        });
    }
}