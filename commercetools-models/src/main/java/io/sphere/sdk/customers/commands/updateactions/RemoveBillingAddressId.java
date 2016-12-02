package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;

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
    private final String addressId;

    private RemoveBillingAddressId(final String addressId) {
        super("removeBillingAddressId");
        this.addressId = addressId;
    }

    public static RemoveBillingAddressId of(final String addressId) {
        return new RemoveBillingAddressId(addressId);
    }

    public String getAddressId() {
        return addressId;
    }
}
