package io.sphere.sdk.orders.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;

/**
 * Deletes an order in SPHERE.IO.
 *
 */
public interface OrderDeleteCommand extends MetaModelReferenceExpansionDsl<Order, OrderDeleteCommand, OrderExpansionModel<Order>>, DeleteCommand<Order> {

    static OrderDeleteCommand of(final Versioned<Order> versioned) {
        return new OrderDeleteCommandImpl(versioned);
    }
}
