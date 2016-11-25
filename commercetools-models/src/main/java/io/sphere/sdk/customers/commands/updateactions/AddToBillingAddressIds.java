package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;

/**
 * Adds an existing address from the {@link Customer#getAddresses()} - referred to by its `id` - to {@link Customer#getBillingAddressIds()}.
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandIntegrationTest#addToBillingAddressIds()}
 *
 *  @see Customer
 */
public final class AddToBillingAddressIds extends UpdateActionImpl<Customer> {
    private final String addressId;

    private AddToBillingAddressIds(final String addressId) {
        super("addtoBillingAddressIds");
        this.addressId = addressId;
    }

    public static AddToBillingAddressIds of(final String addressId) {
        return new AddToBillingAddressIds(addressId);
    }

    public String getAddressId() {
        return addressId;
    }
}
