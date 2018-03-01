package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.TaxPortion;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.List;

/**
 * The total tax amount of the cart can be set if it has the {@link io.sphere.sdk.carts.TaxMode#EXTERNAL_AMOUNT} set.
 */
public final class SetCartTotalTax extends UpdateActionImpl<Cart> {
    private final MonetaryAmount externalTotalGross;

    @Nullable
    private final List<TaxPortion> externalTaxPortions;

    private SetCartTotalTax(final MonetaryAmount externalTotalGross, @Nullable final List<TaxPortion> externalTaxPortions) {
        super("setCartTotalTax");
        this.externalTotalGross = externalTotalGross;
        this.externalTaxPortions = externalTaxPortions;
    }

    public MonetaryAmount getExternalTotalGross() {
        return externalTotalGross;
    }

    @Nullable
    public List<TaxPortion> getExternalTaxPortions() {
        return externalTaxPortions;
    }

    public static SetCartTotalTax of(final MonetaryAmount externalTotalGross, @Nullable final List<TaxPortion> externalTaxPortions) {
        return new SetCartTotalTax(externalTotalGross, externalTaxPortions);
    }

    public static SetCartTotalTax of(final MonetaryAmount externalTotalGross) {
        return of(externalTotalGross, null);
    }
}
