package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;

import javax.annotation.Nullable;

/**
 Sets a new tax rate that overrides the tax rate selected by the platform. If `externalTaxRate` is empty, the platform will select a tax rate as in {@link AddLineItem}.

 {@doc.gen intro}

 {@include.example }

 */
public final class SetCustomLineItemTaxRate extends UpdateActionImpl<Cart> {
    @Nullable
    private final ExternalTaxRateDraft externalTaxRate;
    private final String customLineItemId;

    private SetCustomLineItemTaxRate(final String customLineItemId, @Nullable final ExternalTaxRateDraft externalTaxRate) {
        super("setLineItemTaxRate");
        this.externalTaxRate = externalTaxRate;
        this.customLineItemId = customLineItemId;
    }

    public static SetCustomLineItemTaxRate of(final String customLineItemId, @Nullable final ExternalTaxRateDraft externalTaxRate) {
        return new SetCustomLineItemTaxRate(customLineItemId, externalTaxRate);
    }

    @Nullable
    public ExternalTaxRateDraft getExternalTaxRate() {
        return externalTaxRate;
    }

    public String getCustomLineItemId() {
        return customLineItemId;
    }
}
