package io.sphere.sdk.orders.commands;

import io.sphere.sdk.http.JsonEndpoint;
import io.sphere.sdk.orders.Order;

final class OrdersEndpoint {
    static JsonEndpoint<Order> ENDPOINT = JsonEndpoint.of(Order.typeReference(), "/orders");
}
