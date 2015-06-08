package io.sphere.sdk.orders.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.queries.MetaModelFetchDsl;

/**
 Gets an order by ID.

 {@include.example io.sphere.sdk.orders.commands.OrderFromCartCreateCommandTest#execution()}
 */
public interface OrderByIdFetch extends MetaModelFetchDsl<Order, OrderByIdFetch, OrderExpansionModel<Order>> {
    static OrderByIdFetch of(final Identifiable<Order> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static OrderByIdFetch of(final String id) {
        return new OrderByIdFetchImpl(id);
    }
}

