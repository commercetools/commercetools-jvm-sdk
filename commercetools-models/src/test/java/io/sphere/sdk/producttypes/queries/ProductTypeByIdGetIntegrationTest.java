package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.producttypes.ProductTypeFixtures.withProductType;
import static org.assertj.core.api.Assertions.*;

public class ProductTypeByIdGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() {
        withProductType(client(), productType -> {
            final ProductType loadedProductType = client().executeBlocking(ProductTypeByIdGet.of(productType));
            assertThat(loadedProductType).isEqualTo(productType);
        });
    }
}