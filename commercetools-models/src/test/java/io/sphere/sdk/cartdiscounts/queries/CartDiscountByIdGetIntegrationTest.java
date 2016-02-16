package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.cartdiscounts.CartDiscountFixtures.withPersistentCartDiscount;
import static org.assertj.core.api.Assertions.*;

public class CartDiscountByIdGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final CartDiscount fetchedDiscount = client().executeBlocking(CartDiscountByIdGet.of(cartDiscount));
            assertThat(fetchedDiscount.getId()).isEqualTo(cartDiscount.getId());
        });
    }
}