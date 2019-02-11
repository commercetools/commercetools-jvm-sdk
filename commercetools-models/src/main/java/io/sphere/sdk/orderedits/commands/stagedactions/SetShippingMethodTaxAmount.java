package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.ExternalTaxAmountDraft;

import javax.annotation.Nullable;

public final class SetShippingMethodTaxAmount extends OrderEditStagedUpdateActionBase {

    @Nullable
    private final ExternalTaxAmountDraft externalTaxAmount;

    @JsonCreator
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
