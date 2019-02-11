package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;

import javax.annotation.Nullable;

public final class SetShippingMethodTaxRate extends OrderEditStagedUpdateActionBase {

    @Nullable
    private final ExternalTaxRateDraft externalTaxRate;

    @JsonCreator
    private SetShippingMethodTaxRate(@Nullable final ExternalTaxRateDraft externalTaxRate) {
        super("setShippingMethodTaxRate");
        this.externalTaxRate = externalTaxRate;
    }

    public static SetShippingMethodTaxRate of(@Nullable final ExternalTaxRateDraft externalTaxRate) {
        return new SetShippingMethodTaxRate(externalTaxRate);
    }

    @Nullable
    public ExternalTaxRateDraft getExternalTaxRate() {
        return externalTaxRate;
    }
}
