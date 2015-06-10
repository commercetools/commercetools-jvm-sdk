package io.sphere.sdk.orders.expansion;

import io.sphere.sdk.orders.Order;
import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.queries.ExpansionPath;

import java.util.Optional;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public class OrderExpansionModel<T> extends ExpansionModel<T> {
    public OrderExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    OrderExpansionModel() {
        super();
    }

    public static OrderExpansionModel<Order> of() {
        return new OrderExpansionModel<>();
    }

    public ExpansionPath<T> customerGroup() {
        return pathWithRoots("customerGroup");
    }
}
