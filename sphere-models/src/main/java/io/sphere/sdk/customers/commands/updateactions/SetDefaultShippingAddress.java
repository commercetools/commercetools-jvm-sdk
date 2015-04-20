package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Address;

import java.util.Optional;

import static java.lang.String.format;

/**
 * Sets the default shipping address from the customer's addresses.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandTest#setDefaultShippingAddress()}
 */
public class SetDefaultShippingAddress extends UpdateAction<Customer> {
    private final Optional<String> addressId;

    private SetDefaultShippingAddress(final Optional<String> addressId) {
        super("setDefaultShippingAddress");
        this.addressId = addressId;
    }

    public static SetDefaultShippingAddress of(final Optional<String> addressId) {
        return new SetDefaultShippingAddress(addressId);
    }

    public static SetDefaultShippingAddress of(final String addressId) {
        return of(Optional.of(addressId));
    }

    public static SetDefaultShippingAddress of(final Address address) {
        if (!address.getId().isPresent()) {
            throw new IllegalArgumentException(format("The address %s should have an id.", address));
        }
        return of(address.getId());
    }

    public Optional<String> getAddressId() {
        return addressId;
    }
}
