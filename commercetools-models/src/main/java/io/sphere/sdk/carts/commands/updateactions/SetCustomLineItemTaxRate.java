package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.TaxMode;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;

import javax.annotation.Nullable;

/**
 Sets a tax rate for a line item if the cart has the tax mode {@link TaxMode#EXTERNAL}.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.ExternalTaxRatesIntegrationTest#setCustomLineItemTaxRate()}

 */
public final class SetCustomLineItemTaxRate extends UpdateActionImpl<Cart> {
    @Nullable
    private final ExternalTaxRateDraft externalTaxRate;
    private final String customLineItemId;

    private SetCustomLineItemTaxRate(final String customLineItemId, @Nullable final ExternalTaxRateDraft externalTaxRate) {
        super("setCustomLineItemTaxRate");
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
