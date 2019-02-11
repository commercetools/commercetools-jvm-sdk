package io.sphere.sdk.orderedits.commands.stagedactions;

import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;

import javax.annotation.Nullable;

public final class SetShippingMethod extends OrderEditStagedUpdateActionBase {

    @Nullable
    private final ResourceIdentifier<ShippingMethod> shippingMethod;
    @Nullable
    private final ExternalTaxRateDraft externalTaxRate;

    private SetShippingMethod(@Nullable final ResourceIdentifier<ShippingMethod> shippingMethod, @Nullable final ExternalTaxRateDraft externalTaxRate) {
        super("setShippingMethod");
        this.shippingMethod = shippingMethod;
        this.externalTaxRate = externalTaxRate;
    }

    public static SetShippingMethod of(@Nullable final ResourceIdentifier<ShippingMethod> shippingMethod, @Nullable final ExternalTaxRateDraft externalTaxRate){
        return new SetShippingMethod(shippingMethod, externalTaxRate);
    }

    @Nullable
    public ResourceIdentifier<ShippingMethod> getShippingMethod() {
        return shippingMethod;
    }

    @Nullable
    public ExternalTaxRateDraft getExternalTaxRate() {
        return externalTaxRate;
    }
}
