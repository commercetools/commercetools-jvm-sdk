package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.cartdiscounts.DiscountedLineItemPrice;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

/**
 * Information concerning shipping in a cart.
 *
 * @see io.sphere.sdk.carts.commands.updateactions.SetShippingAddress
 */
@JsonDeserialize(as = CartShippingInfoImpl.class)
@ResourceValue
public interface CartShippingInfo {
    String getShippingMethodName();

    MonetaryAmount getPrice();

    ShippingRate getShippingRate();

    TaxRate getTaxRate();

    Reference<TaxCategory> getTaxCategory();

    @Nullable
    TaxedItemPrice getTaxedPrice();

    @Nullable
    Reference<ShippingMethod> getShippingMethod();

    @Nullable
    DiscountedLineItemPrice getDiscountedPrice();

    ShippingMethodState getShippingMethodState();
}
