package io.sphere.sdk.orders.queries;

import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;

/**
 Gets an order by ID.

 {@include.example io.sphere.sdk.orders.commands.OrderFromCartCreateCommandTest#execution()}
 */
final class OrderByIdFetchImpl extends MetaModelFetchDslImpl<Order, OrderByIdFetch, OrderExpansionModel<Order>> implements OrderByIdFetch {
    OrderByIdFetchImpl(final String id) {
        super(id, OrderEndpoint.ENDPOINT, OrderExpansionModel.of(), OrderByIdFetchImpl::new);
    }

    public OrderByIdFetchImpl(MetaModelFetchDslBuilder<Order, OrderByIdFetch, OrderExpansionModel<Order>> builder) {
        super(builder);
    }
}
