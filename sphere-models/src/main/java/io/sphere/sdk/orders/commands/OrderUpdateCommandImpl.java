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
public class OrderUpdateCommandImpl extends UpdateCommandDslImpl<Order, OrderUpdateCommandImpl> {
    private OrderUpdateCommandImpl(final Versioned<Order> order, final List<? extends UpdateAction<Order>> updateActions) {
        super(order, updateActions, OrderEndpoint.ENDPOINT);
    }

    public static OrderUpdateCommandImpl of(final Versioned<Order> order, final List<? extends UpdateAction<Order>> updateActions) {
        return new OrderUpdateCommandImpl(order, updateActions);
    }

    public static OrderUpdateCommandImpl of(final Versioned<Order> order, final UpdateAction<Order> updateAction) {
        return of(order, asList(updateAction));
    }
}
