package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.taxcategories.TaxMode;

import javax.annotation.Nullable;

/**
 Changes the {@link Cart#getTaxMode() tax mode} of a cart.

 {@doc.gen intro}

 {@include.example }

 */
public final class ChangeTaxMode extends UpdateActionImpl<Cart> {
    @Nullable
    private final TaxMode taxMode;

    private ChangeTaxMode(@Nullable final TaxMode taxMode) {
        super("setShippingMethodTaxRate");
        this.taxMode = taxMode;
    }

    public static ChangeTaxMode of(@Nullable final TaxMode taxMode) {
        return new ChangeTaxMode(taxMode);
    }

    @Nullable
    public TaxMode getTaxMode() {
        return taxMode;
    }
}
