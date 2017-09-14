package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.ExternalTaxAmountDraft;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;

/**
 * A shipping method tax amount can be set if the cart has the {@link io.sphere.sdk.carts.TaxMode#EXTERNAL_AMOUNT} set.
 */
public final class SetShippingMethodTaxAmount extends UpdateActionImpl<Cart> {
    @Nullable
    private final ExternalTaxAmountDraft externalTaxAmount;

    private SetShippingMethodTaxAmount(final ExternalTaxAmountDraft externalTaxAmount) {
        super("setShippingMethodTaxAmount");
        this.externalTaxAmount = externalTaxAmount;
    }

    @Nullable
    public ExternalTaxAmountDraft getExternalTaxAmount() {
        return externalTaxAmount;
    }

    public static SetShippingMethodTaxAmount of(@Nullable final ExternalTaxAmountDraft externalTaxAmount) {
        return new SetShippingMethodTaxAmount(externalTaxAmount);
    }
}
