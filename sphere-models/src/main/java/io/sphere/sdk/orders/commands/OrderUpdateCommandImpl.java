package io.sphere.sdk.orders.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orders.Order;

import java.util.List;


final class OrderUpdateCommandImpl extends UpdateCommandDslImpl<Order, OrderUpdateCommand> implements OrderUpdateCommand {
    OrderUpdateCommandImpl(final Versioned<Order> order, final List<? extends UpdateAction<Order>> updateActions) {
        super(order, updateActions, OrderEndpoint.ENDPOINT);
    }
}
