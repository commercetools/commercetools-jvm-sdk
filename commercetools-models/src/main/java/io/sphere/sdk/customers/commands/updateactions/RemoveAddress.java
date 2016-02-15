package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Address;

import static java.lang.String.format;

/**
 * Removes the address with the given ID from the customer's addresses.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandIntegrationTest#removeAddress()}
 *
 * @see Customer
 */
public final class RemoveAddress extends UpdateActionImpl<Customer> {
    private final String addressId;

    private RemoveAddress(final String addressId) {
        super("removeAddress");
        this.addressId = addressId;
    }

    public static RemoveAddress of(final String addressId) {
        return new RemoveAddress(addressId);
    }

    public static RemoveAddress of(final Address address) {
        if (address.getId() == null) {
            throw new IllegalArgumentException(format("The address %s should have an id.", address));
        }
        return of(address.getId());
    }

    public String getAddressId() {
        return addressId;
    }
}
