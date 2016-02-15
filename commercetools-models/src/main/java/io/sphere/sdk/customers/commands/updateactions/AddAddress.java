package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Address;

/**
 * Adds an address to a customer.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandIntegrationTest#addAddress()}
 *
 * @see Customer
 * @see Customer#getAddresses()
 * @see Customer#getDefaultBillingAddress()
 * @see Customer#getDefaultShippingAddress()
 */
public final class AddAddress extends UpdateActionImpl<Customer> {
    private final Address address;

    private AddAddress(final Address address) {
        super("addAddress");
        this.address = address;
    }

    public static AddAddress of(final Address address) {
        return new AddAddress(address);
    }

    public Address getAddress() {
        return address;
    }
}
