package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

import javax.annotation.Nullable;
import java.util.List;

public final class ShippingInfoExpansionModel<T> extends ExpansionModel<T> {
    public ShippingInfoExpansionModel(final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    public TaxCategoryExpansionModel<T> taxCategory() {
        return TaxCategoryExpansionModel.of(buildPathExpression(), "taxCategory");
    }

    public ShippingMethodExpansionModel<T> shippingMethod() {
        return ShippingMethodExpansionModel.of(buildPathExpression(), "shippingMethod");
    }
}
