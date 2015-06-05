package io.sphere.sdk.orders.queries;

import io.sphere.sdk.orders.Order;
import io.sphere.sdk.queries.ByIdFetchImpl;

/**
 Gets an order by ID.

 {@include.example io.sphere.sdk.orders.commands.OrderFromCartCreateCommandTest#execution()}
 */
public class OrderByIdFetch extends ByIdFetchImpl<Order> {
    private OrderByIdFetch(final String id) {
        super(id, OrderEndpoint.ENDPOINT);
    }

    public static OrderByIdFetch of(final String id) {
        return new OrderByIdFetch(id);
    }
}
