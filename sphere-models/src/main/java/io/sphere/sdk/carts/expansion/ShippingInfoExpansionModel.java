package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ReferenceExpansionSupport;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

import javax.annotation.Nullable;
import java.util.List;

public class ShippingInfoExpansionModel<T> extends ExpansionModel<T> {
    public ShippingInfoExpansionModel(final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    public ReferenceExpansionSupport<T> taxCategory() {
        return expansionPath("taxCategory");
    }

    public ShippingMethodExpansionModel<T> shippingMethod() {
        return new ShippingMethodExpansionModel<>(buildPathExpression(), "shippingMethod");
    }
}
