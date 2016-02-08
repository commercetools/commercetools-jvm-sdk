package io.sphere.sdk.orders.commands;

import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;

final class OrderDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<Order, OrderDeleteCommand, OrderExpansionModel<Order>> implements OrderDeleteCommand {
    OrderDeleteCommandImpl(final Versioned<Order> versioned) {
        super(versioned, OrderEndpoint.ENDPOINT, OrderExpansionModel.of(), OrderDeleteCommandImpl::new);
    }

    OrderDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<Order, OrderDeleteCommand, OrderExpansionModel<Order>> builder) {
        super(builder);
    }
}
