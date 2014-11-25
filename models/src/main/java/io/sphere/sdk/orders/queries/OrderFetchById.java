package io.sphere.sdk.orders.queries;

import io.sphere.sdk.orders.Order;
import io.sphere.sdk.queries.FetchByIdImpl;

/**
 Gets an order by ID.

 {@include.example io.sphere.sdk.orders.commands.OrderFromCartCreateCommandTest#execution()}
 */
public class OrderFetchById extends FetchByIdImpl<Order> {
    public OrderFetchById(final String id) {
        super(id, OrdersEndpoint.ENDPOINT);
    }

    public static OrderFetchById of(final String id) {
        return new OrderFetchById(id);
    }
}
