package io.sphere.sdk.orders.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDsl;

public interface OrderInStoreByIdGet extends MetaModelGetDsl<Order, Order, OrderInStoreByIdGet, OrderExpansionModel<Order>> {

    static OrderInStoreByIdGet of(final String storeKey, final String id) {
        return new OrderInStoreByIdGetImpl(storeKey, id);
    }
    
    static OrderInStoreByIdGet of(final String storeKey, final Identifiable<Order> resource) {
        return of(storeKey, resource.getId());
    }
}