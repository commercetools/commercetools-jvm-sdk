package io.sphere.sdk.orders.queries;

import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;

final class OrderQueryImpl extends MetaModelQueryDslImpl<Order, OrderQuery, OrderQueryModel, OrderExpansionModel<Order>> implements OrderQuery {
    OrderQueryImpl(){
        super(OrderEndpoint.ENDPOINT.endpoint(), OrderQuery.resultTypeReference(), OrderQueryModel.of(), OrderExpansionModel.of(), OrderQueryImpl::new);
    }

    private OrderQueryImpl(final MetaModelQueryDslBuilder<Order, OrderQuery, OrderQueryModel, OrderExpansionModel<Order>> builder) {
        super(builder);
    }
}