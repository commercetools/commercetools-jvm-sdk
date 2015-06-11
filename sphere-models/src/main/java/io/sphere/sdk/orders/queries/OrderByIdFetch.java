package io.sphere.sdk.orders.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.queries.ExpansionPath;
import io.sphere.sdk.queries.MetaModelFetchDsl;

import java.util.List;
import java.util.function.Function;

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

    @Override
    OrderByIdFetch plusExpansionPaths(final Function<OrderExpansionModel<Order>, ExpansionPath<Order>> m);

    @Override
    OrderByIdFetch withExpansionPaths(final Function<OrderExpansionModel<Order>, ExpansionPath<Order>> m);

    @Override
    OrderByIdFetch plusExpansionPaths(final ExpansionPath<Order> expansionPath);

    @Override
    List<ExpansionPath<Order>> expansionPaths();

    @Override
    OrderByIdFetch withExpansionPaths(final ExpansionPath<Order> expansionPath);

    @Override
    OrderByIdFetch withExpansionPaths(final List<ExpansionPath<Order>> expansionPaths);
}

