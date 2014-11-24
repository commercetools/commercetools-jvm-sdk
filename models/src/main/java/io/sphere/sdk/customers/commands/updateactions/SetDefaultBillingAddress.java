package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Address;

import java.util.Optional;

import static java.lang.String.format;

/**
 * Sets the default billing address from the customer's addresses.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandTest#setDefaultBillingAddress()}
 */
public class SetDefaultBillingAddress extends UpdateAction<Customer> {
    private final Optional<String> addressId;

    private SetDefaultBillingAddress(final Optional<String> addressId) {
        super("setDefaultBillingAddress");
        this.addressId = addressId;
    }

    public static SetDefaultBillingAddress of(final Optional<String> addressId) {
        return new SetDefaultBillingAddress(addressId);
    }

    public static SetDefaultBillingAddress of(final String addressId) {
        return of(Optional.of(addressId));
    }

    public static SetDefaultBillingAddress of(final Address address) {
        if (!address.getId().isPresent()) {
            throw new IllegalArgumentException(format("The address %s should have an id.", address));
        }
        return of(address.getId());
    }

    public Optional<String> getAddressId() {
        return addressId;
    }
}
