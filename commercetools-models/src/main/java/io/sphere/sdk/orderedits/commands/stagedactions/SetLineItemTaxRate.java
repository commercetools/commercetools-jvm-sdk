package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;

import javax.annotation.Nullable;

public final class SetLineItemTaxRate extends OrderEditStagedUpdateActionBase {

    private final String lineItemId;

    @Nullable
    private final ExternalTaxRateDraft externalTaxRate;

    @JsonCreator
    private SetLineItemTaxRate(final String lineItemId, @Nullable final ExternalTaxRateDraft externalTaxRate) {
        super("setLineItemTaxRate");
        this.lineItemId = lineItemId;
        this.externalTaxRate = externalTaxRate;
    }

    public static SetLineItemTaxRate of(final String lineItemId, @Nullable final ExternalTaxRateDraft externalTaxRate) {
        return new SetLineItemTaxRate(lineItemId, externalTaxRate);
    }

    @Nullable
    public ExternalTaxRateDraft getExternalTaxRate() {
        return externalTaxRate;
    }

    public String getLineItemId() {
        return lineItemId;
    }

}
