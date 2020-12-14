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
    @Nullable
    private final String addressKey;


    private SetDefaultShippingAddress(@Nullable final String addressId, @Nullable final String addressKey) {
        super("setDefaultShippingAddress");
        this.addressId = addressId;
        this.addressKey = addressKey;
    }

    public static SetDefaultShippingAddress ofKey(final String addressKey) {
        return new SetDefaultShippingAddress(null, addressKey);
    }

    public static SetDefaultShippingAddress of(final String addressId) {
        return new SetDefaultShippingAddress(addressId, null);
    }

    public static SetDefaultShippingAddress ofAddress(final Address address) {
        if (address.getId() == null && address.getKey() == null) {
            throw new IllegalArgumentException(format("The address %s should have an id or a key.", address));
        }
        if (address.getId() != null) {
            return of(address.getId());
        } else {
            return ofKey(address.getKey());
        }
    }

    @Nullable
    public String getAddressId() {
        return addressId;
    }

    @Nullable
    public String getAddressKey() {
        return addressKey;
    }
}
