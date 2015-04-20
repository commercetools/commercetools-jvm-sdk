package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.orders.Order;

/**

 {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandTest#setOrderNumber()}
 */
public class SetOrderNumber extends UpdateAction<Order> {
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
