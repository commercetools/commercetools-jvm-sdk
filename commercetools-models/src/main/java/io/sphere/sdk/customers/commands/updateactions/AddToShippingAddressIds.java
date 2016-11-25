package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Address;

import javax.annotation.Nullable;

import static java.lang.String.format;

/**
 * Adds an existing address from the {@link Customer#getAddresses()} - referred to by its `id` - to {@link Customer#getShippingAddressIds()}.
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandIntegrationTest#addToShippingAddressIds()}
 *
 *  @see Customer
 */
public final class AddToShippingAddressIds extends UpdateActionImpl<Customer> {
    private final String addressId;

    private AddToShippingAddressIds(final String addressId) {
        super("addToShippingAddressIds");
        this.addressId = addressId;
    }

    public static AddToShippingAddressIds of(final String addressId) {
        return new AddToShippingAddressIds(addressId);
    }

    public String getAddressId() {
        return addressId;
    }
}
