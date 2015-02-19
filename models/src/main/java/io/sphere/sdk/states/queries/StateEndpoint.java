package io.sphere.sdk.states.queries;

import io.sphere.sdk.http.JsonEndpoint;
import io.sphere.sdk.orders.Order;

final class StateEndpoint {

    private StateEndpoint() {
    }

    static final JsonEndpoint<Order> ENDPOINT = JsonEndpoint.of(Order.typeReference(), "/states");
}
