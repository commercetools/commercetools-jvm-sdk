package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;

import javax.annotation.Nullable;

public final class SetCustomLineItemTaxRate extends OrderEditStagedUpdateActionBase {

    @Nullable
    private final ExternalTaxRateDraft externalTaxRate;
    private final String customLineItemId;

    @JsonCreator
    private SetCustomLineItemTaxRate(final String customLineItemId, @Nullable final ExternalTaxRateDraft externalTaxRate) {
        super("setCustomLineItemTaxRate");
        this.externalTaxRate = externalTaxRate;
        this.customLineItemId = customLineItemId;
    }

    public static SetCustomLineItemTaxRate of(final String customLineItemId, @Nullable final ExternalTaxRateDraft externalTaxRate) {
        return new SetCustomLineItemTaxRate(customLineItemId, externalTaxRate);
    }

    @Nullable
    public ExternalTaxRateDraft getExternalTaxRate() {
        return externalTaxRate;
    }

    public String getCustomLineItemId() {
        return customLineItemId;
    }
}
