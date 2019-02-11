package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.annotation.Nullable;

public final class SetShippingAddressAndCustomShippingMethod extends OrderEditStagedUpdateActionBase {

    private final Address address;

    private final String shippingMethodName;

    private final ShippingRate shippingRate;

    @Nullable
    private final ResourceIdentifier<TaxCategory> taxCategory;

    @Nullable
    private final ExternalTaxRateDraft externalTaxRate;

    @JsonCreator
    private SetShippingAddressAndCustomShippingMethod(final Address address,
                                                      final String shippingMethodName,
                                                      final ShippingRate shippingRate,
                                                      @Nullable final ResourceIdentifier<TaxCategory> taxCategory,
                                                      @Nullable final ExternalTaxRateDraft externalTaxRate) {
        super("setShippingAddressAndCustomShippingMethod");
        this.address = address;
        this.shippingMethodName = shippingMethodName;
        this.shippingRate = shippingRate;
        this.taxCategory = taxCategory;
        this.externalTaxRate = externalTaxRate;
    }

    public static SetShippingAddressAndCustomShippingMethod of(final Address address,
                                                               final String shippingMethodName,
                                                               final ShippingRate shippingRate,
                                                               @Nullable final ResourceIdentifier<TaxCategory> taxCategory,
                                                               @Nullable final ExternalTaxRateDraft externalTaxRate) {
        return new SetShippingAddressAndCustomShippingMethod(address, shippingMethodName, shippingRate, taxCategory, externalTaxRate);
    }

    public static SetShippingAddressAndCustomShippingMethod of(final Address address,
                                                               final String shippingMethodName,
                                                               final ShippingRate shippingRate) {
        return new SetShippingAddressAndCustomShippingMethod(address, shippingMethodName, shippingRate, null, null);
    }

    public Address getAddress() {
        return address;
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
