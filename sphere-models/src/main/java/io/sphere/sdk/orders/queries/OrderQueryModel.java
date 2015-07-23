package io.sphere.sdk.orders.queries;

import io.sphere.sdk.carts.queries.CartLikeQueryModel;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderState;
import io.sphere.sdk.orders.PaymentState;
import io.sphere.sdk.orders.ShipmentState;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.SphereEnumerationQueryModel;

public class OrderQueryModel extends CartLikeQueryModel<Order> {
    private OrderQueryModel(final QueryModel<Order> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public static OrderQueryModel of() {
        return new OrderQueryModel(null, null);
    }

    public SphereEnumerationQueryModel<Order, OrderState> orderState() {
        return enumerationQueryModel("orderState");
    }

    public SphereEnumerationQueryModel<Order, ShipmentState> shipmentState() {
        return enumerationQueryModel("shipmentState");
    }

    public SphereEnumerationQueryModel<Order, PaymentState> paymentState() {
        return enumerationQueryModel("paymentState");
    }

    public SyncInfoQueryModel<Order> syncInfo() {
        return new SyncInfoQueryModelImpl<>(this, "syncInfo");
    }
}
