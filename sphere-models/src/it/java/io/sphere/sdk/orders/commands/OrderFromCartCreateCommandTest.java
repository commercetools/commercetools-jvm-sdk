package io.sphere.sdk.orders.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartState;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.carts.CartFixtures.withFilledCart;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderFromCartCreateCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withFilledCart(client(), cart -> {
            final OrderFromCartCreateCommand createCommand =
                    OrderFromCartCreateCommand.of(cart).plusExpansionPaths(m -> m.cart());
            final Order order = client().executeBlocking(createCommand);
            assertThat(order.getLineItems()).isEqualTo(cart.getLineItems());
            assertThat(order.getCustomLineItems()).isEqualTo(cart.getCustomLineItems());
            assertThat(order.getCart().getId()).isEqualTo(cart.getId());
            final Cart orderedCart = order.getCart().getObj();
            assertThat(orderedCart).isNotNull();
            assertThat(orderedCart.getId()).isEqualTo(cart.getId());
            assertThat(orderedCart.getCartState()).isEqualTo(CartState.ORDERED);
        });
    }
}