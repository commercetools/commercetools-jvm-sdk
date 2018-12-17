package io.sphere.sdk.orders;


import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.cartdiscounts.DiscountedLineItemPrice;
import io.sphere.sdk.carts.ShippingMethodState;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.List;

@ResourceDraftValue(factoryMethods = @FactoryMethod(parameterNames = {"shippingMethodName", "price", "shippingRate", "taxRate", "taxCategory", "shippingMethod", "shippingMethodState", "deliveries"}))
public interface ShippingInfoImportDraft {

    List<Delivery> getDeliveries();

    MonetaryAmount getPrice();

    String getShippingMethodName();

    ShippingRate getShippingRate();

    ShippingMethodState getShippingMethodState();

    @Nullable
    ResourceIdentifier<ShippingMethod> getShippingMethod();

    @Nullable
    ResourceIdentifier<TaxCategory> getTaxCategory();

    @Nullable
    TaxRate getTaxRate();

    @Nullable
    DiscountedLineItemPrice getDiscountedPrice();

}
