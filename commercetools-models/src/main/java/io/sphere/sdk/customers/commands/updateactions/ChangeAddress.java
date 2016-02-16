package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Address;

import static java.lang.String.format;

/**
 * Replaces the address with the given ID, with the new address in the customer's addresses array. The new address will have the same ID.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandIntegrationTest#changeAddress()}
 *
 * @see Customer
 */
public final class ChangeAddress extends UpdateActionImpl<Customer> {
    private final Address address;
    private final String addressId;

    private ChangeAddress(final String addressId, final Address address) {
        super("changeAddress");
        this.address = address;
        this.addressId = addressId;
    }

    public static ChangeAddress of(final String addressId, final Address address) {
        return new ChangeAddress(addressId, address);
    }

    public static ChangeAddress ofOldAddressToNewAddress(final Address oldAddress, final Address newAddress) {
        if (oldAddress.getId() == null) {
            throw new IllegalArgumentException(format("The address %s should have an id.", oldAddress));
        }
        return of(oldAddress.getId(), newAddress);
    }

    public Address getAddress() {
        return address;
    }

    public String getAddressId() {
        return addressId;
    }
}
