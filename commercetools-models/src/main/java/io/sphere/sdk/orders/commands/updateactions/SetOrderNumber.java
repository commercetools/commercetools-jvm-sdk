package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.orders.Order;

/**
 Sets the order number.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#setOrderNumber()}
 */
public final class SetOrderNumber extends UpdateActionImpl<Order> {
    private final String orderNumber;

    private SetOrderNumber(final String orderNumber) {
        super("setOrderNumber");
        this.orderNumber = orderNumber;
    }

    public static SetOrderNumber of(final String orderNumber) {
        return new SetOrderNumber(orderNumber);
    }

    public String getOrderNumber() {
        return orderNumber;
    }
}
