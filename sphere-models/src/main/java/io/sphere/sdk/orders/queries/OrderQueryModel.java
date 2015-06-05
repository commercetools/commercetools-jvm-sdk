package io.sphere.sdk.orders.queries;

import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.OrderState;
import io.sphere.sdk.orders.PaymentState;
import io.sphere.sdk.orders.ShipmentState;
import io.sphere.sdk.queries.*;

import java.util.Optional;

public class OrderQueryModel<T> extends DefaultModelQueryModelImpl<T> {
    private OrderQueryModel(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public static OrderQueryModel<Order> of() {
        return new OrderQueryModel<>(Optional.<QueryModel<Order>>empty(), Optional.<String>empty());
    }

    public StringQuerySortingModel<T> customerId() {
        return stringModel("customerId");
    }

    public StringQuerySortingModel<T> customerEmail() {
        return stringModel("customerEmail");
    }

    public SphereEnumerationQueryModel<T, OrderState> orderState() {
        return new SphereEnumerationQueryModel<>(Optional.of(this), Optional.of("orderState"));
    }

    public SphereEnumerationQueryModel<T, ShipmentState> shipmentState() {
        return new SphereEnumerationQueryModel<>(Optional.of(this), Optional.of("shipmentState"));
    }

    public SphereEnumerationQueryModel<T, PaymentState> paymentState() {
        return new SphereEnumerationQueryModel<>(Optional.of(this), Optional.of("paymentState"));
    }

    public SyncInfoQueryModel<T> syncInfo() {
        return new SyncInfoQueryModel<>(Optional.of(this), Optional.of("syncInfo"));
    }

    public CountryQueryModel<T> country() {
        return new CountryQueryModel<>(Optional.of(this), Optional.of("country"));
    }
}
