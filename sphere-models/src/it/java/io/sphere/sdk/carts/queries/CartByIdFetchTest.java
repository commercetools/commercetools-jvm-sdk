package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.carts.CartFixtures.createCartWithCountry;
import static org.assertj.core.api.Assertions.assertThat;

public class CartByIdFetchTest extends IntegrationTest {
    @Test
    public void fetchById() throws Exception {
        final Cart cart = createCartWithCountry(client());
        final String id = cart.getId();
        final Optional<Cart> fetchedCartOptional = execute(CartByIdFetch.of(id));
        assertThat(fetchedCartOptional).contains(cart);
    }
}