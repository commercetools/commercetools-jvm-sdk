package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.TaxCategory;

/**
 Sets a custom shipping method (independent of the shipping methods defined in the project). The custom shipping method can be unset with the setShippingMethod action without the shippingMethod.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandTest#setCustomShippingMethod()}
 */
public final class SetCustomShippingMethod extends UpdateActionImpl<Cart> {

    private final String shippingMethodName;
    private final ShippingRate shippingRate;
    private final Reference<TaxCategory> taxCategory;

    private SetCustomShippingMethod(final String shippingMethodName, final ShippingRate shippingRate,
                                   final Referenceable<TaxCategory> taxCategory) {
        super("setCustomShippingMethod");
        this.shippingMethodName = shippingMethodName;
        this.shippingRate = shippingRate;
        this.taxCategory = taxCategory.toReference();
    }

    public static SetCustomShippingMethod of(final String shippingMethodName, final ShippingRate shippingRate,
                                      final Referenceable<TaxCategory> taxCategory) {
        return new SetCustomShippingMethod(shippingMethodName, shippingRate, taxCategory);
    }

    public String getShippingMethodName() {
        return shippingMethodName;
    }

    public ShippingRate getShippingRate() {
        return shippingRate;
    }

    public Reference<TaxCategory> getTaxCategory() {
        return taxCategory;
    }
}
