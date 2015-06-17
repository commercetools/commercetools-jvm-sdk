package io.sphere.sdk.orders.expansion;

import io.sphere.sdk.carts.expansion.CartLikeExpansionModel;
import io.sphere.sdk.orders.Order;

import java.util.Optional;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public class OrderExpansionModel<T> extends CartLikeExpansionModel<T> {
    public OrderExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, path);
    }

    OrderExpansionModel() {
        super();
    }

    public SyncInfoExpansionModel<T> syncInfo() {
        //since it is a set, there is no method with index access
        return new SyncInfoExpansionModel<>(pathExpressionOption(), "syncInfo[*]");
    }

    public static OrderExpansionModel<Order> of() {
        return new OrderExpansionModel<>();
    }
}
