package io.sphere.sdk.orders.commands;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.commands.MetaModelUpdateCommandDslBuilder;
import io.sphere.sdk.commands.MetaModelUpdateCommandDslImpl;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;

import java.util.List;

final class OrderInStoreUpdateByOrderNumberCommandImpl extends MetaModelUpdateCommandDslImpl<Order, OrderInStoreUpdateByOrderNumberCommand, OrderExpansionModel<Order>> implements OrderInStoreUpdateByOrderNumberCommand {

    OrderInStoreUpdateByOrderNumberCommandImpl(final String storeKey, final Versioned<Order> versioned, final List<? extends UpdateAction<Order>> updateActions) {
        super(versioned, updateActions, JsonEndpoint.of(Order.typeReference(), "/in-store/key=" + storeKey + "/orders"), OrderInStoreUpdateByOrderNumberCommandImpl::new, OrderExpansionModel.of());
    }

    OrderInStoreUpdateByOrderNumberCommandImpl(final MetaModelUpdateCommandDslBuilder<Order, OrderInStoreUpdateByOrderNumberCommand, OrderExpansionModel<Order>> builder) {
        super(builder);
    }
}
