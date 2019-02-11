package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.ExternalTaxAmountDraft;

import javax.annotation.Nullable;

public final class SetLineItemTaxAmount extends OrderEditStagedUpdateActionBase {

    private final String lineItemId;

    @Nullable
    private final ExternalTaxAmountDraft externalTaxAmount;

    @JsonCreator
    private SetLineItemTaxAmount(final String lineItemId, final ExternalTaxAmountDraft externalTaxAmount) {
        super("setLineItemTaxAmount");
        this.lineItemId = lineItemId;
        this.externalTaxAmount = externalTaxAmount;
    }

    public String getLineItemId() {
        return lineItemId;
    }

    @Nullable
    public ExternalTaxAmountDraft getExternalTaxAmount() {
        return externalTaxAmount;
    }

    public static SetLineItemTaxAmount of(final String lineItemId, @Nullable final ExternalTaxAmountDraft externalTaxAmount) {
        return new SetLineItemTaxAmount(lineItemId, externalTaxAmount);
    }
}