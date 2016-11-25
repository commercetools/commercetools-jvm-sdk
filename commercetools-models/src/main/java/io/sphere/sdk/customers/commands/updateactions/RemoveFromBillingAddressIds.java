package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;

/**
 * Removes an existing billing address from the {@link Customer#getBillingAddressIds()}.
 If the billing address is the Customer's default billing address the {@link Customer#getDefaultBillingAddressId()} will be unset.
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandIntegrationTest#removeFromBillingAddressIds()}
 *
 *  @see Customer
 */
public final class RemoveFromBillingAddressIds extends UpdateActionImpl<Customer> {
    private final String addressId;

    private RemoveFromBillingAddressIds(final String addressId) {
        super("removeFromBillingAddressIds");
        this.addressId = addressId;
    }

    public static RemoveFromBillingAddressIds of(final String addressId) {
        return new RemoveFromBillingAddressIds(addressId);
    }

    public String getAddressId() {
        return addressId;
    }
}
