package io.sphere.sdk.orders.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;

import java.util.Collections;
import java.util.List;

/**
 {@doc.gen list actions}
 */
public interface OrderUpdateCommand extends UpdateCommandDsl<Order, OrderUpdateCommand>, MetaModelReferenceExpansionDsl<Order, OrderUpdateCommand, OrderExpansionModel<Order>> {
    static OrderUpdateCommand of(final Versioned<Order> order, final List<? extends UpdateAction<Order>> updateActions) {
        return new OrderUpdateCommandImpl(order, updateActions);
    }

    static OrderUpdateCommand of(final Versioned<Order> order, final UpdateAction<Order> updateAction) {
        return of(order, Collections.singletonList(updateAction));
    }
}
