package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.TaxMode;


public final class ChangeTaxMode extends OrderEditStagedUpdateActionBase {

    private final TaxMode taxMode;

    @JsonCreator
    private ChangeTaxMode(final TaxMode taxMode) {
        super("changeTaxMode");
        this.taxMode = taxMode;
    }

    public static ChangeTaxMode of(final TaxMode taxMode) {
        return new ChangeTaxMode(taxMode);
    }

    public TaxMode getTaxMode() {
        return taxMode;
    }
}
