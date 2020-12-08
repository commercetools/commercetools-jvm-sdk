package io.sphere.sdk.orders.commands;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class OrderInStoreDeleteByIdCommandImpl extends MetaModelByIdDeleteCommandImpl<Order, OrderInStoreDeleteByIdCommand, OrderExpansionModel<Order>> implements OrderInStoreDeleteByIdCommand {

    OrderInStoreDeleteByIdCommandImpl(final String storeKey, final Versioned<Order> versioned, final boolean eraseData) {
        super(versioned,eraseData, JsonEndpoint.of(Order.typeReference(), "/in-store/key=" + urlEncode(storeKey) + "/orders"), OrderExpansionModel.of(), OrderInStoreDeleteByIdCommandImpl::new);
    }

    OrderInStoreDeleteByIdCommandImpl(final MetaModelByIdDeleteCommandBuilder<Order, OrderInStoreDeleteByIdCommand, OrderExpansionModel<Order>> builder) {
        super(builder);
    }
}
