package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;

/**
 * Adds an existing address from the {@link Customer#getAddresses()} - referred to by its `id` - to {@link Customer#getBillingAddressIds()}.
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandIntegrationTest#addBillingAddressId()}
 *
 *  @see Customer
 */
public final class AddBillingAddressId extends UpdateActionImpl<Customer> {
    private final String addressId;

    private AddBillingAddressId(final String addressId) {
        super("addBillingAddressId");
        this.addressId = addressId;
    }

    public static AddBillingAddressId of(final String addressId) {
        return new AddBillingAddressId(addressId);
    }

    public String getAddressId() {
        return addressId;
    }
}
