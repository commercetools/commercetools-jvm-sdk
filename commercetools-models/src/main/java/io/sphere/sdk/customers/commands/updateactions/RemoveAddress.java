package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Address;

import javax.annotation.Nullable;

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
    @Nullable
    private final String addressId;
    @Nullable
    private final String addressKey;

    private RemoveAddress(@Nullable final String addressId, @Nullable String addressKey) {
        super("removeAddress");
        this.addressId = addressId;
        this.addressKey = addressKey;
    }

    public static RemoveAddress of(final String addressId) {
        return new RemoveAddress(addressId, null);
    }

    public static RemoveAddress ofKey(final String addressKey) {
        return new RemoveAddress(null, addressKey);
    }

    public static RemoveAddress of(final Address address) {
        if (address.getId() == null && address.getKey() == null) {
            throw new IllegalArgumentException(format("The address %s should have an id or a key.", address));
        }
        if (address.getId() != null) {
            return of(address.getId());
        } else {
            return ofKey(address.getKey());
        }
    }

    public String getAddressId() {
        return addressId;
    }

    public String getAddressKey() {
        return addressKey;
    }
}
