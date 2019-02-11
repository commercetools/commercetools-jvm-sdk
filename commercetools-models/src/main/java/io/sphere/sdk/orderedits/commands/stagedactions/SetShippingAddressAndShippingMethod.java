package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;

import javax.annotation.Nullable;

public final class SetShippingAddressAndShippingMethod extends OrderEditStagedUpdateActionBase {

    private final Address address;

    @Nullable
    private final ResourceIdentifier<ShippingMethod> shippingMethod;

    @Nullable
    private final ExternalTaxRateDraft externalTaxRate;

    @JsonCreator
    private SetShippingAddressAndShippingMethod(final Address address, final @Nullable ResourceIdentifier<ShippingMethod> shippingMethod, final @Nullable ExternalTaxRateDraft externalTaxRate) {
        super("setShippingAddressAndShippingMethod");
        this.address = address;
        this.shippingMethod = shippingMethod;
        this.externalTaxRate = externalTaxRate;
    }

    public static SetShippingAddressAndShippingMethod of(final Address address, final @Nullable ResourceIdentifier<ShippingMethod> shippingMethod, final @Nullable ExternalTaxRateDraft externalTaxRate) {
        return new SetShippingAddressAndShippingMethod(address, shippingMethod, externalTaxRate);
    }

    public static SetShippingAddressAndShippingMethod of(final Address address) {
        return new SetShippingAddressAndShippingMethod(address, null, null);
    }

    public Address getAddress() {
        return address;
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
