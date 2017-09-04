package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.ExternalTaxAmountDraft;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;

/**
 * A custom line item tax amount can be set if the cart has the {@link io.sphere.sdk.carts.TaxMode#EXTERNAL_AMOUNT} set.
 */
public final class SetCustomLineItemTaxAmount extends UpdateActionImpl<Cart> {
    private final String customLineItemId;

    @Nullable
    private final ExternalTaxAmountDraft externalTaxAmount;

    private SetCustomLineItemTaxAmount(final String customLineItemId, final ExternalTaxAmountDraft externalTaxAmount) {
        super("setCustomLineItemTaxAmount");
        this.customLineItemId = customLineItemId;
        this.externalTaxAmount = externalTaxAmount;
    }

    public String getCustomLineItemId() {
        return customLineItemId;
    }

    @Nullable
    public ExternalTaxAmountDraft getExternalTaxAmount() {
        return externalTaxAmount;
    }

    public static SetCustomLineItemTaxAmount of(final String customLineItemId, @Nullable final ExternalTaxAmountDraft externalTaxAmount) {
        return new SetCustomLineItemTaxAmount(customLineItemId, externalTaxAmount);
    }
}
