package io.sphere.sdk.shippingmethods.expansion;

import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.queries.ExpansionPath;
import io.sphere.sdk.shippingmethods.ShippingMethod;

import java.util.Optional;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public class ShippingMethodExpansionModel<T> extends ExpansionModel<T> {
    public ShippingMethodExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    ShippingMethodExpansionModel() {
        super();
    }

    public ExpansionPath<T> taxCategory() {
        return expansionPath("taxCategory");
    }

    public static ShippingMethodExpansionModel<ShippingMethod> of() {
        return new ShippingMethodExpansionModel<>();
    }
}
