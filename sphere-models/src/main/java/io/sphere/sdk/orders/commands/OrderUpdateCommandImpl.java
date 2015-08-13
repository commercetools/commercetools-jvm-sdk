package io.sphere.sdk.orders.commands;

import io.sphere.sdk.commands.*;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;

import java.util.List;


final class OrderUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<Order, OrderUpdateCommand, OrderExpansionModel<Order>> implements OrderUpdateCommand {
    OrderUpdateCommandImpl(final Versioned<Order> order, final List<? extends UpdateAction<Order>> updateActions) {
        super(order, updateActions, OrderEndpoint.ENDPOINT, OrderUpdateCommandImpl::new, OrderExpansionModel.of());
    }

    OrderUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<Order, OrderUpdateCommand, OrderExpansionModel<Order>> builder) {
        super(builder);
    }
}
