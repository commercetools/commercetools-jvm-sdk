package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.carts.TaxMode;

import javax.annotation.Nullable;

/**
 Changes the {@link Cart#getTaxMode() tax mode} of a cart.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.ExternalTaxRatesIntegrationTest#changeTaxMode()}

 */
public final class ChangeTaxMode extends UpdateActionImpl<Cart> {
    private final TaxMode taxMode;

    private ChangeTaxMode(final TaxMode taxMode) {
        super("changeTaxMode");
        this.taxMode = taxMode;
    }

    public static ChangeTaxMode of(final TaxMode taxMode) {
        return new ChangeTaxMode(taxMode);
    }

    @Nullable
    public TaxMode getTaxMode() {
        return taxMode;
    }
}
