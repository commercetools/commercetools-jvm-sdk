package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.TaxCalculationMode;

public final class ChangeTaxCalculationMode extends OrderEditStagedUpdateActionBase {

    private final TaxCalculationMode taxCalculationMode;

    @JsonCreator
    private ChangeTaxCalculationMode(final TaxCalculationMode taxCalculationMode) {
        super("changeTaxCalculationMode");
        this.taxCalculationMode = taxCalculationMode;
    }

    public static ChangeTaxCalculationMode of(final TaxCalculationMode taxCalculationMode) {
        return new ChangeTaxCalculationMode(taxCalculationMode);
    }

    public TaxCalculationMode getTaxCalculationMode() {
        return taxCalculationMode;
    }
}
