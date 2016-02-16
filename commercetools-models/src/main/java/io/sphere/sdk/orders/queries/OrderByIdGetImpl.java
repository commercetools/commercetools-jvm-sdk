package io.sphere.sdk.orders.queries;

import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

/**
 Gets an order by ID.

 {@include.example io.sphere.sdk.orders.commands.OrderFromCartCreateCommandIntegrationTest#execution()}
 */
final class OrderByIdGetImpl extends MetaModelGetDslImpl<Order, Order, OrderByIdGet, OrderExpansionModel<Order>> implements OrderByIdGet {
    OrderByIdGetImpl(final String id) {
        super(id, OrderEndpoint.ENDPOINT, OrderExpansionModel.of(), OrderByIdGetImpl::new);
    }

    public OrderByIdGetImpl(MetaModelGetDslBuilder<Order, Order, OrderByIdGet, OrderExpansionModel<Order>> builder) {
        super(builder);
    }
}
