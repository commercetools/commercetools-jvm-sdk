package io.sphere.sdk.carts;

import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;
import static io.sphere.sdk.carts.CartFixtures.*;

public class CartOriginIntegrationTest extends IntegrationTest {

    @Test
    public void testCartOriginCustomer() throws Exception {
        CartDraft draft = CartDraftBuilder.of(SphereTestUtils.EUR).origin(CartOrigin.CUSTOMER).build();
        withCartDraft(client(), draft, cart -> {

            assertThat(cart.getOrigin()).isEqualTo(CartOrigin.CUSTOMER);
            return cart;
        });
    }

    @Test
    public void testCartOriginMerchant() throws Exception {
        CartDraft draft = CartDraftBuilder.of(SphereTestUtils.EUR).origin(CartOrigin.MERCHANT).build();
        withCartDraft(client(), draft, cart -> {

            assertThat(cart.getOrigin()).isEqualTo(CartOrigin.MERCHANT);
            return cart;
        });
    }
}
