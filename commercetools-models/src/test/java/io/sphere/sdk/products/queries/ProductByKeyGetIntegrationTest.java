package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductByKeyGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() {
        final String key = randomKey();
        ProductFixtures.withProduct(client(), builder -> builder.key(key), product -> {
            final ProductByKeyGet request = ProductByKeyGet.of(key);
            final Product loadedProduct = client().executeBlocking(request);
            assertThat(loadedProduct.getId()).isEqualTo(product.getId());
        });
    }
}