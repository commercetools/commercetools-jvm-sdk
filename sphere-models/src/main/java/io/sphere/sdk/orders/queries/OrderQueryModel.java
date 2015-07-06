package io.sphere.sdk.orders.queries;

import io.sphere.sdk.carts.queries.CartLikeQueryModel;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderState;
import io.sphere.sdk.orders.PaymentState;
import io.sphere.sdk.orders.ShipmentState;
import io.sphere.sdk.queries.*;

import java.util.Optional;

public class OrderQueryModel extends CartLikeQueryModel<Order> {
    private OrderQueryModel(final Optional<? extends QueryModel<Order>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public static OrderQueryModel of() {
        return new OrderQueryModel(Optional.<QueryModel<Order>>empty(), Optional.<String>empty());
    }

    public SphereEnumerationQueryModel<Order, OrderState> orderState() {
        return new SphereEnumerationQueryModel<>(Optional.of(this), Optional.of("orderState"));
    }

    public SphereEnumerationQueryModel<Order, ShipmentState> shipmentState() {
        return new SphereEnumerationQueryModel<>(Optional.of(this), Optional.of("shipmentState"));
    }

    public SphereEnumerationQueryModel<Order, PaymentState> paymentState() {
        return new SphereEnumerationQueryModel<>(Optional.of(this), Optional.of("paymentState"));
    }

    public SyncInfoQueryModel<Order> syncInfo() {
        return new SyncInfoQueryModel<>(Optional.of(this), Optional.of("syncInfo"));
    }
}
