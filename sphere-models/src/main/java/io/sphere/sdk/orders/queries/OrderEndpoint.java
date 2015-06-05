package io.sphere.sdk.orders.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.orders.Order;

final class OrderEndpoint {
    static final JsonEndpoint<Order> ENDPOINT = JsonEndpoint.of(Order.typeReference(), "/orders");
}
