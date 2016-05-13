package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;

import javax.annotation.Nullable;

/**
 Sets a new tax rate that overrides the tax rate selected by the platform.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.ExternalTaxRatesIntegrationTest#setShippingMethodTaxRate()}

 */
public final class SetShippingMethodTaxRate extends UpdateActionImpl<Cart> {
    @Nullable
    private final ExternalTaxRateDraft externalTaxRate;

    private SetShippingMethodTaxRate(@Nullable final ExternalTaxRateDraft externalTaxRate) {
        super("setShippingMethodTaxRate");
        this.externalTaxRate = externalTaxRate;
    }

    public static SetShippingMethodTaxRate of(@Nullable final ExternalTaxRateDraft externalTaxRate) {
        return new SetShippingMethodTaxRate(externalTaxRate);
    }

    @Nullable
    public ExternalTaxRateDraft getExternalTaxRate() {
        return externalTaxRate;
    }
}
