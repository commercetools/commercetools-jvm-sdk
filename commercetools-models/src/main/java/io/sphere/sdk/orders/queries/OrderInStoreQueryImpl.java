package io.sphere.sdk.orders.queries;

import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

class OrderInStoreQueryImpl extends MetaModelQueryDslImpl<Order, OrderInStoreQuery, OrderQueryModel, OrderExpansionModel<Order>> implements OrderInStoreQuery {

    OrderInStoreQueryImpl(final String storeKey){
        super("/in-store/key=" + urlEncode(storeKey) + "/orders", OrderInStoreQuery.resultTypeReference(), OrderQueryModel.of(), OrderExpansionModel.of(), OrderInStoreQueryImpl::new);
    }

    private OrderInStoreQueryImpl(final MetaModelQueryDslBuilder<Order, OrderInStoreQuery, OrderQueryModel, OrderExpansionModel<Order>> builder) {
        super(builder);
    }
}
