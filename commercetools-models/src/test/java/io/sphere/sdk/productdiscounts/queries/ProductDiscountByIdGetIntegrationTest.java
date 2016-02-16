package io.sphere.sdk.productdiscounts.queries;

import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.productdiscounts.ProductDiscountFixtures.withUpdateableProductDiscount;
import static org.assertj.core.api.Assertions.*;

public class ProductDiscountByIdGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withUpdateableProductDiscount(client(), productDiscount -> {
            final ProductDiscount discount = client().executeBlocking(ProductDiscountByIdGet.of(productDiscount));
            assertThat(discount.getId()).isEqualTo(productDiscount.getId());
            return productDiscount;
        });
    }
}