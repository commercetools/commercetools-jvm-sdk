package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Address;

public class AddAddress extends UpdateAction<Customer> {
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
