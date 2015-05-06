package io.sphere.sdk.orders.commands;

import io.sphere.sdk.orders.Order;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.carts.CartFixtures.withFilledCart;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderFromCartCreateCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withFilledCart(client(), cart -> {
            final Order order = execute(OrderFromCartCreateCommand.of(cart));
            assertThat(order.getLineItems()).isEqualTo(cart.getLineItems());
            assertThat(order.getCustomLineItems()).isEqualTo(cart.getCustomLineItems());
        });
    }
}