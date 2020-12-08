package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;

import javax.annotation.Nullable;

/**
 * Removes an existing billing address from the {@link Customer#getBillingAddressIds()}.
 If the billing address is the Customer's default billing address the {@link Customer#getDefaultBillingAddressId()} will be unset.
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandIntegrationTest#removeBillingAddressId()}
 *
 *  @see Customer
 */
public final class RemoveBillingAddressId extends UpdateActionImpl<Customer> {
    @Nullable
    private final String addressId;
    @Nullable
    private final String addressKey;

    private RemoveBillingAddressId(@Nullable final String addressId, @Nullable final String addressKey) {
        super("removeBillingAddressId");
        this.addressId = addressId;
        this.addressKey = addressKey;
    }

    public static RemoveBillingAddressId of(final String addressId) {
        return new RemoveBillingAddressId(addressId, null);
    }

    public static RemoveBillingAddressId ofKey(final String addressKey) {
        return new RemoveBillingAddressId(null, addressKey);
    }

    public String getAddressId() {
        return addressId;
    }

    public String getAddressKey() {
        return addressKey;
    }
}
