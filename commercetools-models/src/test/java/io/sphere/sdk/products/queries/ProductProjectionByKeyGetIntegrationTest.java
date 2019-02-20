package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.ProductFixtures;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Ignore;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

//TODO FIX
@Ignore
public class ProductProjectionByKeyGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() {
        final String key = randomKey();
        ProductFixtures.withProduct(client(), builder -> builder.key(key), product -> {
            final ProductProjectionByKeyGet request = ProductProjectionByKeyGet.ofStaged(key);
            final ProductProjection productProjection = client().executeBlocking(request);
            assertThat(productProjection.getId()).isEqualTo(product.getId());
        });
    }
}