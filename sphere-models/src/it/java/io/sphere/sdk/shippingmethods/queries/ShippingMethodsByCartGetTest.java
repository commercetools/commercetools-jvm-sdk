package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.carts.CartFixtures.*;


public class ShippingMethodsByCartGetTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final Cart cart = createCartWithShippingAddress(client());

        final List<ShippingMethod> result =
                client().executeBlocking(ShippingMethodsByCartGet.of(cart));
    }
}