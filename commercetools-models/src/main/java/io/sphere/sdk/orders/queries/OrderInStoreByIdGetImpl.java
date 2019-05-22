package io.sphere.sdk.orders.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

class OrderInStoreByIdGetImpl extends MetaModelGetDslImpl<Order, Order, OrderInStoreByIdGet, OrderExpansionModel<Order>> implements OrderInStoreByIdGet {

    OrderInStoreByIdGetImpl(final String storeKey, final String id) {
        super(id, JsonEndpoint.of(Order.typeReference(), "/in-store/key=" + storeKey + "/orders"), OrderExpansionModel.of(), OrderInStoreByIdGetImpl::new);
    }

    public OrderInStoreByIdGetImpl(final MetaModelGetDslBuilder<Order, Order, OrderInStoreByIdGet, OrderExpansionModel<Order>> builder) {
        super(builder);
    }
}