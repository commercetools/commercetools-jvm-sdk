package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Address;

import javax.annotation.Nullable;

import static java.lang.String.format;

/**
 * Sets the default billing address from the customer's addresses.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandIntegrationTest#setDefaultBillingAddress()}
 * @see Customer
 */
public final class SetDefaultBillingAddress extends UpdateActionImpl<Customer> {
    private final String addressId;
    private final String addressKey;

    private SetDefaultBillingAddress(@Nullable final String addressId, String addressKey) {
        super("setDefaultBillingAddress");
        this.addressId = addressId;
        this.addressKey = addressKey;
    }

    public static SetDefaultBillingAddress of(@Nullable final String addressId, @Nullable final String addressKey) {
        return new SetDefaultBillingAddress(addressId, addressKey);
    }

    public static SetDefaultBillingAddress ofAddress(final Address address) {
        if (address.getId() == null) {
            throw new IllegalArgumentException(format("The address %s should have an id.", address));
        }
        return of(address.getId(), address.getKey());
    }

    @Nullable
    public String getAddressId() {
        return addressId;
    }
}
