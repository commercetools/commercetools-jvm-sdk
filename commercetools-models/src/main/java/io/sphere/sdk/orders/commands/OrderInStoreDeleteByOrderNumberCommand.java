package io.sphere.sdk.orders.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

public interface OrderInStoreDeleteByOrderNumberCommand extends MetaModelReferenceExpansionDsl<Order, OrderInStoreDeleteByOrderNumberCommand, OrderExpansionModel<Order>>, DeleteCommand<Order> {

    static OrderInStoreDeleteByOrderNumberCommand of(final String storeKey, final String orderNumber, final Long version, boolean eraseData) {
        final Versioned<Order> versioned = Versioned.of("order-number=" + urlEncode(orderNumber), version);//hack for simple reuse
        return new OrderInStoreDeleteByOrderNumberCommandImpl(storeKey, versioned, eraseData);
    }

    static OrderInStoreDeleteByOrderNumberCommand of(final String storeKey, final String orderNumber, final Long version) {
        final Versioned<Order> versioned = Versioned.of("order-number=" + urlEncode(orderNumber), version);//hack for simple reuse
        return new OrderInStoreDeleteByOrderNumberCommandImpl(storeKey, versioned, false);
    }
    
}
