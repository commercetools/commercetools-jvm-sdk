package io.sphere.sdk.orders.commands;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderFromCartDraft;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class OrderFromCartInStoreCreateCommandImpl extends MetaModelCreateCommandImpl<Order, OrderFromCartInStoreCreateCommand, OrderFromCartDraft, OrderExpansionModel<Order>> implements OrderFromCartInStoreCreateCommand {

    OrderFromCartInStoreCreateCommandImpl(final MetaModelCreateCommandBuilder<Order, OrderFromCartInStoreCreateCommand, OrderFromCartDraft, OrderExpansionModel<Order>> builder) {
        super(builder);
    }

    OrderFromCartInStoreCreateCommandImpl(final String storeKey, final OrderFromCartDraft draft) {
        super(draft, JsonEndpoint.of(Order.typeReference(), "/in-store/key=" + urlEncode(storeKey) + "/orders"), OrderExpansionModel.of(), OrderFromCartInStoreCreateCommandImpl::new);
    }

    @Override
    public OrderFromCartInStoreCreateCommandImpl withDraft(final OrderFromCartDraft draft) {
        return new OrderFromCartInStoreCreateCommandImpl(copyBuilder().draft(draft));
    }
}
