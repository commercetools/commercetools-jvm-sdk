package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.RoundingMode;

import javax.annotation.Nullable;

public final class ChangeTaxRoundingMode extends OrderEditStagedUpdateActionBase {

    private final RoundingMode taxRoundingMode;

    @JsonCreator
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