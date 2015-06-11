package io.sphere.sdk.orders.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderFromCartDraft;

public class OrderFromCartCreateCommand extends CreateCommandImpl<Order, OrderFromCartDraft> {
    private OrderFromCartCreateCommand(final OrderFromCartDraft draft) {
        super(draft, OrderEndpoint.ENDPOINT);
    }

    public static OrderFromCartCreateCommand of(final OrderFromCartDraft draft) {
        return new OrderFromCartCreateCommand(draft);
    }

    public static OrderFromCartCreateCommand of(final Versioned<Cart> cart) {
        return new OrderFromCartCreateCommand(OrderFromCartDraft.of(cart));
    }
}
