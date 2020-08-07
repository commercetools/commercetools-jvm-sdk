package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.ProductFixtures;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.ArrayList;

import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductProjectionByKeyGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() {
        final String key = randomKey();
        ProductFixtures.withProduct(client(), builder -> builder.key(key), product -> {
            final ArrayList<String> localeProjection = new ArrayList<String>();
            localeProjection.add("en-en");
            final ProductProjectionByKeyGet request = ProductProjectionByKeyGet.ofStaged(key)
                    .withLocaleProjection(localeProjection);
            final ProductProjection productProjection = client().executeBlocking(request);
            assertThat(productProjection.getId()).isEqualTo(product.getId());
        });
    }
}