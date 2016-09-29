package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

public interface ShippingInfoExpansionModel<T> {
    TaxCategoryExpansionModel<T> taxCategory();

    ShippingMethodExpansionModel<T> shippingMethod();

    DiscountedLineItemPriceExpansionModel<T> discountedPrice();
}
