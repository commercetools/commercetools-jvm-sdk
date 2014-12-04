package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderState;

/**

 {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandTest#changeOrderState()}
 */
public class ChangeOrderState extends UpdateAction<Order> {
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
