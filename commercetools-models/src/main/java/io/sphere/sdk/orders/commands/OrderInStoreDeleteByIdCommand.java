package io.sphere.sdk.orders.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;

public interface OrderInStoreDeleteByIdCommand extends MetaModelReferenceExpansionDsl<Order, OrderInStoreDeleteByIdCommand, OrderExpansionModel<Order>>, DeleteCommand<Order>  {
    
    static OrderInStoreDeleteByIdCommand of(final String storeKey, final Versioned<Order> versioned, final boolean eraseData) {
        return new OrderInStoreDeleteByIdCommandImpl(storeKey, versioned, eraseData);
    }
    
    static OrderInStoreDeleteByIdCommand of(final String storeKey, final Versioned<Order> versioned) {
        return new OrderInStoreDeleteByIdCommandImpl(storeKey, versioned,false);
    }
}