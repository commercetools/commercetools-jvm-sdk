package io.sphere.sdk.productdiscounts.queries;

import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.productdiscounts.ProductDiscountFixtures.withUpdateableProductDiscount;
import static org.assertj.core.api.Assertions.*;

public class ProductDiscountByKeyGetIntegrationTest extends IntegrationTest {

    @Test
    public void execution() throws Exception {
        withUpdateableProductDiscount(client(), productDiscount -> {
            final ProductDiscount discount = client().executeBlocking(ProductDiscountByKeyGet.of(productDiscount.getKey()));
            assertThat(discount.getId()).isEqualTo(productDiscount.getId());
            assertThat(discount.getKey()).isEqualTo(productDiscount.getKey());
            return productDiscount;
        });
    }
}