package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;

import javax.annotation.Nullable;

/**
 * Adds an existing address from the {@link Customer#getAddresses()} - referred to by its `id` - to {@link Customer#getBillingAddressIds()}.
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandIntegrationTest#addBillingAddressId()}
 *
 *  @see Customer
 */
public final class AddBillingAddressId extends UpdateActionImpl<Customer> {
    @Nullable
    private final String addressId;
    @Nullable
    private final String addressKey;

    private AddBillingAddressId(@Nullable final String addressId, @Nullable final String addressKey) {
        super("addBillingAddressId");
        this.addressId = addressId;
        this.addressKey = addressKey;
    }

    public static AddBillingAddressId of(final String addressId) {
        return new AddBillingAddressId(addressId, null);
    }

    public static AddBillingAddressId ofKey(final String addressKey) {
        return new AddBillingAddressId(null, addressKey);
    }

    public String getAddressId() {
        return addressId;
    }

    public String getAddressKey() {
        return addressKey;
    }
}
