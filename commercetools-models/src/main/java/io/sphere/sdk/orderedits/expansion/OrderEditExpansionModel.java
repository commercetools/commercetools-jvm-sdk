package io.sphere.sdk.orderedits.expansion;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.expansion.OrderExpansionModel;

import java.util.List;

public interface OrderEditExpansionModel<T> extends ExpansionPathContainer<T> {

    OrderExpansionModel<T> resource();

    static OrderEditExpansionModel<OrderEdit> of() {
        return new OrderEditExpansionModelImpl<>();
    }

    static <T> OrderEditExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new OrderEditExpansionModelImpl<>(parentPath, path);
    }

}
