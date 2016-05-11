package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;

import javax.annotation.Nullable;

/**
 Sets a new tax rate that overrides the tax rate selected by the platform. If `externalTaxRate` is empty, the platform will select a tax rate as in {@link AddLineItem}.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.ExternalTaxRatesIntegrationTest#setLineItemTaxRate()}

 */
public final class SetLineItemTaxRate extends UpdateActionImpl<Cart> {
    private final String lineItemId;
    @Nullable
    private final ExternalTaxRateDraft externalTaxRate;

    private SetLineItemTaxRate(final String lineItemId, @Nullable final ExternalTaxRateDraft externalTaxRate) {
        super("setLineItemTaxRate");
        this.lineItemId = lineItemId;
        this.externalTaxRate = externalTaxRate;
    }

    public static SetLineItemTaxRate of(final String lineItemId, @Nullable final ExternalTaxRateDraft externalTaxRate) {
        return new SetLineItemTaxRate(lineItemId, externalTaxRate);
    }

    @Nullable
    public ExternalTaxRateDraft getExternalTaxRate() {
        return externalTaxRate;
    }

    public String getLineItemId() {
        return lineItemId;
    }
}
