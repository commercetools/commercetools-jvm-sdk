package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryFixtures;
import io.sphere.sdk.categories.queries.CategoryByKeyGet;
import io.sphere.sdk.queries.Get;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.cartdiscounts.CartDiscountFixtures.withPersistentCartDiscount;
import static org.assertj.core.api.Assertions.assertThat;

public class CartDiscountByKeyGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final String key = cartDiscount.getKey();
            final CartDiscount loadedCartDiscount = client().executeBlocking(CartDiscountByKeyGet.of(key));
            assertThat(loadedCartDiscount.getId()).isEqualTo(cartDiscount.getId());
            assertThat(loadedCartDiscount.getKey()).isEqualTo(key);
        });
    }
}
