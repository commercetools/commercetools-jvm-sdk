package io.sphere.sdk.orders.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;

/**
 * Fetches an order by a known order number.
 *
 * @see Order
 * @see Order#getOrderNumber()
 */
public interface OrderByOrderNumberGet extends MetaModelGetDsl<Order, Order, OrderByOrderNumberGet, OrderExpansionModel<Order>> {
    static OrderByOrderNumberGet of(final String orderNumber) {
        return new OrderByOrderNumberGetImpl(orderNumber);
    }

    @Override
    List<ExpansionPath<Order>> expansionPaths();

    @Override
    OrderByOrderNumberGet plusExpansionPaths(final ExpansionPath<Order> expansionPath);

    @Override
    OrderByOrderNumberGet withExpansionPaths(final ExpansionPath<Order> expansionPath);

    @Override
    OrderByOrderNumberGet withExpansionPaths(final List<ExpansionPath<Order>> expansionPaths);
}
