package io.sphere.sdk.orders.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orders.Order;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public class OrderUpdateCommand extends UpdateCommandDslImpl<Order> {
    private OrderUpdateCommand(final Versioned<Order> order, final List<? extends UpdateAction<Order>> updateActions) {
        super(order, updateActions, OrdersEndpoint.ENDPOINT);
    }

    public static OrderUpdateCommand of(final Versioned<Order> order, final List<? extends UpdateAction<Order>> updateActions) {
        return new OrderUpdateCommand(order, updateActions);
    }

    public static OrderUpdateCommand of(final Versioned<Order> order, final UpdateAction<Order> updateAction) {
        return of(order, asList(updateAction));
    }
}
