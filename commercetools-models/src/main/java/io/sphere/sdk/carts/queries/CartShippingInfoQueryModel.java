package io.sphere.sdk.carts.queries;

import io.sphere.sdk.queries.MoneyQueryModel;
import io.sphere.sdk.queries.ReferenceOptionalQueryModel;
import io.sphere.sdk.queries.StringQueryModel;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.taxcategories.queries.TaxRateQueryModel;

public interface CartShippingInfoQueryModel<T> {
    StringQueryModel<T> shippingMethodName();

    MoneyQueryModel<T> price();

    TaxRateQueryModel<T> taxRate();

    ReferenceOptionalQueryModel<T, ShippingMethod> shippingMethod();

    DiscountedLineItemPriceQueryModel<T> discountedPrice();
}
