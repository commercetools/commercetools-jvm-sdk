package io.sphere.sdk.orders.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class OrderInStoreByOrderNumberGetImpl extends MetaModelGetDslImpl<Order, Order, OrderInStoreByOrderNumberGet, OrderExpansionModel<Order>> implements OrderInStoreByOrderNumberGet {

    OrderInStoreByOrderNumberGetImpl(final String storeKey, final String orderNumber) {
        super("order-number=" + orderNumber, JsonEndpoint.of(Order.typeReference(), "/in-store/key=" + urlEncode(storeKey) + "/orders"), OrderExpansionModel.of(), OrderInStoreByOrderNumberGetImpl::new);
    }

    public OrderInStoreByOrderNumberGetImpl(final MetaModelGetDslBuilder<Order, Order, OrderInStoreByOrderNumberGet, OrderExpansionModel<Order>> builder) {
        super(builder);
    }

}
