package io.sphere.sdk.orders.queries;

import io.sphere.sdk.orders.Order;
import io.sphere.sdk.queries.*;

import java.util.Optional;

public class OrderQueryModel extends DefaultModelQueryModelImpl<Order> {
    private OrderQueryModel(final Optional<? extends QueryModel<Order>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    static OrderQueryModel get() {
        return new OrderQueryModel(Optional.<QueryModel<Order>>empty(), Optional.<String>empty());
    }

    public StringQuerySortingModel<Order> customerId() {
        return stringModel("customerId");
    }

    public StringQuerySortingModel<Order> customerEmail() {
        return stringModel("customerEmail");
    }

    public CountryQueryModel<Order> country() {
        return new CountryQueryModel<>(Optional.of(this), Optional.of("country"));
    }
}
