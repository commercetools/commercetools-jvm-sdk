package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.utils.VrapRequestDecorator;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomerAndCart;
import static org.assertj.core.api.Assertions.assertThat;

public class CartByCustomerIdGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomerAndCart(client(), (customer, cart) -> {
            final SphereRequest<Cart> sphereRequest =
                    new VrapRequestDecorator<>(CartByCustomerIdGet.of(customer.getId()),"response");

            final Cart fetchedCart = client().executeBlocking(sphereRequest);
            assertThat(fetchedCart.getId()).isEqualTo(cart.getId());
        });
    }
}