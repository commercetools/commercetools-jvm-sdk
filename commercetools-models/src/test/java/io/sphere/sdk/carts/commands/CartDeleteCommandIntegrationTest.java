package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartFixtures;
import io.sphere.sdk.carts.queries.CartByIdGet;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class CartDeleteCommandIntegrationTest extends IntegrationTest {

    @Test
    public void deleteCartWithDataErasureTests() {
        Cart cart = CartFixtures.createCartWithCountry(client());
        client().executeBlocking(CartDeleteCommand.of(cart, true));
        Cart cartQueried = client().executeBlocking(CartByIdGet.of(cart));
        Assertions.assertThat(cartQueried).isNull();
    }

}
