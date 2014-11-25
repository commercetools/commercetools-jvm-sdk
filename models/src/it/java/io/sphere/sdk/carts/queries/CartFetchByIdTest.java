package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.carts.CartFixtures.createCartWithCountry;
import static io.sphere.sdk.test.OptionalAssert.assertThat;

public class CartFetchByIdTest extends IntegrationTest {
    @Test
    public void fetchById() throws Exception {
        final Cart cart = createCartWithCountry(client());
        final Optional<Cart> fetchedCartOptional = execute(CartFetchById.of(cart.getId()));
        assertThat(fetchedCartOptional).isPresentAs(cart);
    }
}