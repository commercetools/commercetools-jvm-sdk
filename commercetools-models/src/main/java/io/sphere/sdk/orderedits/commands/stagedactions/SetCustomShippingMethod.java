package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.annotation.Nullable;

public final class SetCustomShippingMethod extends OrderEditStagedUpdateActionBase {

    private final String shippingMethodName;

    private final ShippingRate shippingRate;

    @Nullable
    private final ResourceIdentifier<TaxCategory> taxCategory;

    @Nullable
    private final ExternalTaxRateDraft externalTaxRate;

    @JsonCreator
    private SetCustomShippingMethod(final String shippingMethodName, final ShippingRate shippingRate, @Nullable final ResourceIdentifier<TaxCategory> taxCategory, @Nullable final ExternalTaxRateDraft externalTaxRate) {
        super("setCustomShippingMethod");
        this.shippingMethodName = shippingMethodName;
        this.shippingRate = shippingRate;
        this.taxCategory = taxCategory;
        this.externalTaxRate = externalTaxRate;
    }

    public static SetCustomShippingMethod of(final String shippingMethodName, final ShippingRate shippingRate, @Nullable final ResourceIdentifier<TaxCategory> taxCategory, @Nullable final ExternalTaxRateDraft externalTaxRateDraft){
        return new SetCustomShippingMethod(shippingMethodName, shippingRate, taxCategory, externalTaxRateDraft);
    }

    public String getShippingMethodName() {
        return shippingMethodName;
    }

    public ShippingRate getShippingRate() {
        return shippingRate;
    }

    @Nullable
    public ResourceIdentifier<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    @Nullable
    public ExternalTaxRateDraft getExternalTaxRate() {
        return externalTaxRate;
    }
}
