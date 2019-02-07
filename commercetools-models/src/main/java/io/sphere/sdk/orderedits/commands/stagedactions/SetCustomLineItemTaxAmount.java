package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.ExternalTaxAmountDraft;

import javax.annotation.Nullable;

public final class SetCustomLineItemTaxAmount extends OrderEditStagedUpdateActionBase {

    private final String customLineItemId;

    @Nullable
    private final ExternalTaxAmountDraft externalTaxAmount;

    @JsonCreator
    private SetCustomLineItemTaxAmount(final String customLineItemId, final ExternalTaxAmountDraft externalTaxAmount) {
        super("setCustomLineItemTaxAmount");
        this.customLineItemId = customLineItemId;
        this.externalTaxAmount = externalTaxAmount;
    }

    public String getCustomLineItemId() {
        return customLineItemId;
    }

    @Nullable
    public ExternalTaxAmountDraft getExternalTaxAmount() {
        return externalTaxAmount;
    }

    public static SetCustomLineItemTaxAmount of(final String customLineItemId, @Nullable final ExternalTaxAmountDraft externalTaxAmount) {
        return new SetCustomLineItemTaxAmount(customLineItemId, externalTaxAmount);
    }

}
