package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.RoundingMode;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;

/**
 Changes the {@link Cart#getTaxRoundingMode()} () tax rounding mode} of a cart.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.TaxRoundingModeIntegrationTest#cartTaxRoundingModeWithTaxesExcludedFromPrice()}

 */
public final class ChangeTaxRoundingMode extends UpdateActionImpl<Cart> {
    private final RoundingMode taxRoundingMode;

    private ChangeTaxRoundingMode(final RoundingMode taxRoundingMode) {
        super("changeTaxRoundingMode");
        this.taxRoundingMode = taxRoundingMode;
    }

    public static ChangeTaxRoundingMode of(final RoundingMode taxRoundingMode) {
        return new ChangeTaxRoundingMode(taxRoundingMode);
    }

    @Nullable
    public RoundingMode getTaxRoundingMode() {
        return taxRoundingMode;
    }
}
