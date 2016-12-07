package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;

/**
 * Removes an existing shipping address from the {@link Customer#getShippingAddressIds()}.
 If the shipping address is the Customer's default shipping address the {@link Customer#getDefaultShippingAddressId()} will be unset.
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandIntegrationTest#removeShippingAddressId()}
 *
 *  @see Customer
 */
public final class RemoveShippingAddressId extends UpdateActionImpl<Customer> {
    private final String addressId;

    private RemoveShippingAddressId(final String addressId) {
        super("removeShippingAddressId");
        this.addressId = addressId;
    }

    public static RemoveShippingAddressId of(final String addressId) {
        return new RemoveShippingAddressId(addressId);
    }

    public String getAddressId() {
        return addressId;
    }
}
