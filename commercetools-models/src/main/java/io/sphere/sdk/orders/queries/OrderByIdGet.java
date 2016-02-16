package io.sphere.sdk.orders.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;
import java.util.function.Function;

/**
 Gets an order by ID.

 {@include.example io.sphere.sdk.orders.commands.OrderFromCartCreateCommandIntegrationTest#execution()}
 */
public interface OrderByIdGet extends MetaModelGetDsl<Order, Order, OrderByIdGet, OrderExpansionModel<Order>> {
    static OrderByIdGet of(final Identifiable<Order> order) {
        return of(order.getId());
    }

    static OrderByIdGet of(final String id) {
        return new OrderByIdGetImpl(id);
    }

    @Override
    OrderByIdGet plusExpansionPaths(final ExpansionPath<Order> expansionPath);

    @Override
    List<ExpansionPath<Order>> expansionPaths();

    @Override
    OrderByIdGet withExpansionPaths(final ExpansionPath<Order> expansionPath);

    @Override
    OrderByIdGet withExpansionPaths(final List<ExpansionPath<Order>> expansionPaths);
}

