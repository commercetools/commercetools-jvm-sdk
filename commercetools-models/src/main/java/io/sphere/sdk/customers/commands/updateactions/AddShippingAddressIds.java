package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;

import static java.lang.String.format;

/**
 * Adds an existing address from the {@link Customer#getAddresses()} - referred to by its `id` - to {@link Customer#getShippingAddressIds()}.
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandIntegrationTest#addShippingAddressId()}
 *
 *  @see Customer
 */
public final class AddShippingAddressIds extends UpdateActionImpl<Customer> {
    private final String addressId;

    private AddShippingAddressIds(final String addressId) {
        super("addShippingAddressId");
        this.addressId = addressId;
    }

    public static AddShippingAddressIds of(final String addressId) {
        return new AddShippingAddressIds(addressId);
    }

    public String getAddressId() {
        return addressId;
    }
}
