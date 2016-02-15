package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.carts.CartFixtures.createCartWithCountry;
import static org.assertj.core.api.Assertions.assertThat;

public class CartByIdGetIntegrationTest extends IntegrationTest {
    @Test
    public void fetchById() throws Exception {
        final Cart cart = createCartWithCountry(client());
        final String id = cart.getId();
        final Cart fetchedCart = client().executeBlocking(CartByIdGet.of(id));
        assertThat(fetchedCart).isEqualTo(cart);
    }
}