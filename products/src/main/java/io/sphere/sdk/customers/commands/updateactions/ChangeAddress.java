package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Address;

import static java.lang.String.format;

public class ChangeAddress extends UpdateAction<Customer> {
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
        if (!oldAddress.getId().isPresent()) {
            throw new IllegalArgumentException(format("The address %s should have an id.", oldAddress));
        }
        return of(oldAddress.getId().get(), newAddress);
    }

    public Address getAddress() {
        return address;
    }

    public String getAddressId() {
        return addressId;
    }
}
