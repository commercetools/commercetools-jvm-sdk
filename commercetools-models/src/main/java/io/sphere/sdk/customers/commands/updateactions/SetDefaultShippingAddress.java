package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Address;

import javax.annotation.Nullable;

import static java.lang.String.format;

/**
 * Sets the default shipping address from the customer's addresses.
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandIntegrationTest#setDefaultShippingAddress()}
 *
 *  @see Customer
 */
public final class SetDefaultShippingAddress extends UpdateActionImpl<Customer> {
    @Nullable
    private final String addressId;

    private SetDefaultShippingAddress(@Nullable final String addressId) {
        super("setDefaultShippingAddress");
        this.addressId = addressId;
    }

    public static SetDefaultShippingAddress of(@Nullable final String addressId) {
        return new SetDefaultShippingAddress(addressId);
    }

    public static SetDefaultShippingAddress ofAddress(final Address address) {
        if (address.getId() == null) {
            throw new IllegalArgumentException(format("The address %s should have an id.", address));
        }
        return of(address.getId());
    }

    @Nullable
    public String getAddressId() {
        return addressId;
    }
}
