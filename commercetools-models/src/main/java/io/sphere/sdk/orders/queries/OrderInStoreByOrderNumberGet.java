package io.sphere.sdk.orders.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;

public interface OrderInStoreByOrderNumberGet extends MetaModelGetDsl<Order, Order, OrderInStoreByOrderNumberGet, OrderExpansionModel<Order>> {
    
    static OrderInStoreByOrderNumberGet of(final String storeKey, final String orderNumber) {
        return new OrderInStoreByOrderNumberGetImpl(storeKey, orderNumber);
    }

    @Override
    List<ExpansionPath<Order>> expansionPaths();

    @Override
    OrderInStoreByOrderNumberGet plusExpansionPaths(final ExpansionPath<Order> expansionPath);

    @Override
    OrderInStoreByOrderNumberGet withExpansionPaths(final ExpansionPath<Order> expansionPath);

    @Override
    OrderInStoreByOrderNumberGet withExpansionPaths(final List<ExpansionPath<Order>> expansionPaths);
}