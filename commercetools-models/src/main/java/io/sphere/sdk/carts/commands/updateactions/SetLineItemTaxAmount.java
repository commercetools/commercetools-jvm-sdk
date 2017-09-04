package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.ExternalTaxAmountDraft;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;

/**
 * A line item tax amount can be set if the cart has the {@link io.sphere.sdk.carts.TaxMode#EXTERNAL_AMOUNT} set.
 */
public final class SetLineItemTaxAmount extends UpdateActionImpl<Cart> {
    private final String lineItemId;

    @Nullable
    private final ExternalTaxAmountDraft externalTaxAmount;

    private SetLineItemTaxAmount(final String lineItemId, final ExternalTaxAmountDraft externalTaxAmount) {
        super("setLineItemTaxAmount");
        this.lineItemId = lineItemId;
        this.externalTaxAmount = externalTaxAmount;
    }

    public String getLineItemId() {
        return lineItemId;
    }

    @Nullable
    public ExternalTaxAmountDraft getExternalTaxAmount() {
        return externalTaxAmount;
    }

    public static SetLineItemTaxAmount of(final String lineItemId, @Nullable final ExternalTaxAmountDraft externalTaxAmount) {
        return new SetLineItemTaxAmount(lineItemId, externalTaxAmount);
    }
}
