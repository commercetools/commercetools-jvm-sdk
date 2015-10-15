package io.sphere.sdk.orders.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;

/**
 * Deletes an order in SPHERE.IO.
 *
 */
public interface OrderDeleteCommand extends ByIdDeleteCommand<Order>, MetaModelExpansionDsl<Order, OrderDeleteCommand, OrderExpansionModel<Order>> {

    static OrderDeleteCommand of(final Versioned<Order> versioned) {
        return new OrderDeleteCommandImpl(versioned);
    }
}
