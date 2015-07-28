package io.sphere.sdk.orders.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderFromCartDraft;

public class OrderFromCartCreateCommandImpl extends CreateCommandImpl<Order, OrderFromCartDraft> {
    private OrderFromCartCreateCommandImpl(final OrderFromCartDraft draft) {
        super(draft, OrderEndpoint.ENDPOINT);
    }

    public static OrderFromCartCreateCommandImpl of(final OrderFromCartDraft draft) {
        return new OrderFromCartCreateCommandImpl(draft);
    }

    public static OrderFromCartCreateCommandImpl of(final Versioned<Cart> cart) {
        return new OrderFromCartCreateCommandImpl(OrderFromCartDraft.of(cart));
    }
}
