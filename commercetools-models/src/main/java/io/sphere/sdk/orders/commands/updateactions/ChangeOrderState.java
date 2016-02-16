package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderState;

/**
 Changes the order state.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#changeOrderState()}
 */
public final class ChangeOrderState extends UpdateActionImpl<Order> {
    private final OrderState orderState;

    private ChangeOrderState(final OrderState orderState) {
        super("changeOrderState");
        this.orderState = orderState;
    }

    public static ChangeOrderState of(final OrderState orderState) {
        return new ChangeOrderState(orderState);
    }

    public OrderState getOrderState() {
        return orderState;
    }
}
