package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

import javax.annotation.Nullable;
import java.util.List;

final class ShippingInfoExpansionModelImpl<T> extends ExpansionModel<T> implements ShippingInfoExpansionModel<T> {
    public ShippingInfoExpansionModelImpl(final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    @Override
    public TaxCategoryExpansionModel<T> taxCategory() {
        return TaxCategoryExpansionModel.of(buildPathExpression(), "taxCategory");
    }

    @Override
    public ShippingMethodExpansionModel<T> shippingMethod() {
        return ShippingMethodExpansionModel.of(buildPathExpression(), "shippingMethod");
    }
}
