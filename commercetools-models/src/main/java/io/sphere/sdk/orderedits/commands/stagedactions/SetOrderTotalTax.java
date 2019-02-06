package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.TaxPortion;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.List;

public final class SetOrderTotalTax extends OrderEditStagedUpdateActionBase {

    private final MonetaryAmount externalTotalGross;

    private final List<TaxPortion> externalTaxPortions;

    @JsonCreator
    private SetOrderTotalTax(final MonetaryAmount externalTotalGross, @Nullable final List<TaxPortion> externalTaxPortions){
        super("setOrderTotalTax");
        this.externalTotalGross = externalTotalGross;
        this.externalTaxPortions = externalTaxPortions;
    }

    public static SetOrderTotalTax of(final MonetaryAmount externalTotalGross, @Nullable final List<TaxPortion> externalTaxPortions) {
        return new SetOrderTotalTax(externalTotalGross, externalTaxPortions);
    }

    public static SetOrderTotalTax of(final MonetaryAmount externalTotalGross) {
        return new SetOrderTotalTax(externalTotalGross, null);
    }

    public MonetaryAmount getExternalTotalGross() {
        return externalTotalGross;
    }

    public List<TaxPortion> getExternalTaxPortions() {
        return externalTaxPortions;
    }
}
