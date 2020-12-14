package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;

import javax.annotation.Nullable;

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
    @Nullable
    private final String addressId;
    @Nullable
    private final String addressKey;


    private RemoveShippingAddressId(@Nullable final String addressId, @Nullable final String addressKey) {
        super("removeShippingAddressId");
        this.addressId = addressId;
        this.addressKey = addressKey;
    }

    public static RemoveShippingAddressId of(final String addressId) {
        return new RemoveShippingAddressId(addressId, null);
    }
    public static RemoveShippingAddressId ofKey(final String addressKey) {
        return new RemoveShippingAddressId(null, addressKey);
    }

    public String getAddressId() {
        return addressId;
    }

    public String getAddressKey() {
        return addressKey;
    }
}
