package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class CartDiscountByKeyGetIntegrationTest extends IntegrationTest {

    @Test
    public void execution() throws Exception {
        CartDiscountFixtures.withCartDiscount(client(), cartDiscount -> {
            final CartDiscount cd = client().executeBlocking(CartDiscountByKeyGet.of(cartDiscount.getKey()));
            Assertions.assertThat(cd).isNotNull();
            Assertions.assertThat(cd.getKey()).isEqualTo(cartDiscount.getKey());
            return cartDiscount;
        });
    }
    
}
