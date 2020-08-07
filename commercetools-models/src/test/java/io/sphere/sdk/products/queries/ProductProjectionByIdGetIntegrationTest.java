package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.products.ProductProjectionType.STAGED;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionByIdGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withProduct(client(), "getProductProjectionById", product -> {
            final ProductProjectionType projectionType = STAGED;
            final ArrayList<String> localeProjection = new ArrayList<String>();
            localeProjection.add("en-en");
            final String productId = product.getId();
            final ProductProjectionByIdGet sphereRequest = ProductProjectionByIdGet.of(productId, projectionType)
                    .withLocaleProjection(localeProjection);
            final ProductProjection productProjection = client().executeBlocking(sphereRequest);
            final String fetchedProjectionId = productProjection.getId();
            assertThat(fetchedProjectionId).isEqualTo(productId);
            assertThat(productProjection.getCategories()).isEqualTo(product.getMasterData().get(projectionType).getCategories());
        });
    }
}