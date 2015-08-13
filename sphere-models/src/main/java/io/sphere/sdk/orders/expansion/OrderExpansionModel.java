package io.sphere.sdk.orders.expansion;

import io.sphere.sdk.carts.expansion.CartLikeExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.orders.Order;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public class OrderExpansionModel<T> extends CartLikeExpansionModel<T> {
    private OrderExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    OrderExpansionModel() {
        super();
    }

    public SyncInfoExpansionModel<T> syncInfo() {
        //since it is a set, there is no method with index access
        return new SyncInfoExpansionModel<>(pathExpression(), "syncInfo[*]");
    }

    public ExpansionPath<T> cart() {
        return expansionPath("cart");
    }

    public static OrderExpansionModel<Order> of() {
        return new OrderExpansionModel<>();
    }
}
