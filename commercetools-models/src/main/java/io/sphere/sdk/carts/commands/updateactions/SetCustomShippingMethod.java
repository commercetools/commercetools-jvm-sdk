package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.annotation.Nullable;

/**
 Sets a custom shipping method (independent of the shipping methods defined in the project). The custom shipping method can be unset with the setShippingMethod action without the shippingMethod.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#setCustomShippingMethod()}
 */
public final class SetCustomShippingMethod extends UpdateActionImpl<Cart> {

    private final String shippingMethodName;
    private final ShippingRate shippingRate;
    @Nullable
    private final Reference<TaxCategory> taxCategory;
    @Nullable
    private final ExternalTaxRateDraft externalTaxRate;

    private SetCustomShippingMethod(final String shippingMethodName, final ShippingRate shippingRate,
                                    @Nullable final Referenceable<TaxCategory> taxCategory, final ExternalTaxRateDraft externalTaxRate) {
        super("setCustomShippingMethod");
        this.shippingMethodName = shippingMethodName;
        this.shippingRate = shippingRate;
        this.externalTaxRate = externalTaxRate;
        this.taxCategory = taxCategory != null ? taxCategory.toReference() : null;
    }

    public static SetCustomShippingMethod of(final String shippingMethodName, final ShippingRate shippingRate,
                                      final Referenceable<TaxCategory> taxCategory) {
        return new SetCustomShippingMethod(shippingMethodName, shippingRate, taxCategory, null);
    }

    public static SetCustomShippingMethod ofExternalTaxCalculation(final String shippingMethodName, final ShippingRate shippingRate,
                                                                   final ExternalTaxRateDraft externalTaxRate) {
        return new SetCustomShippingMethod(shippingMethodName, shippingRate, null, externalTaxRate);
    }

    public static SetCustomShippingMethod ofExternalTaxCalculation(final String shippingMethodName, final ShippingRate shippingRate) {
        return new SetCustomShippingMethod(shippingMethodName, shippingRate, null, null);
    }

    public String getShippingMethodName() {
        return shippingMethodName;
    }

    public ShippingRate getShippingRate() {
        return shippingRate;
    }

    @Nullable
    public Reference<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    @Nullable
    public ExternalTaxRateDraft getExternalTaxRate() {
        return externalTaxRate;
    }
}
