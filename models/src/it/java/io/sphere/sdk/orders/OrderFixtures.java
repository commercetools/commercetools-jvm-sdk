package io.sphere.sdk.orders;

import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.orders.commands.OrderFromCartCreateCommand;

import java.util.function.Consumer;

import static io.sphere.sdk.carts.CartFixtures.withFilledCart;

public class OrderFixtures {
    public static void withOrder(final TestClient client, final Consumer<Order> f) {
        withFilledCart(client, cart -> {
            final Order order = client.execute(OrderFromCartCreateCommand.of(cart));
            f.accept(order);
        });
    }
}
