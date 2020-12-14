package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;

import javax.annotation.Nullable;

import static java.lang.String.format;

/**
 * Adds an existing address from the {@link Customer#getAddresses()} - referred to by its `id` - to {@link Customer#getShippingAddressIds()}.
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandIntegrationTest#addShippingAddressId()}
 *
 *  @see Customer
 */
public final class AddShippingAddressId extends UpdateActionImpl<Customer> {
    private final String addressId;
    private final String addressKey;

    private AddShippingAddressId(@Nullable final String addressId, @Nullable final String addressKey) {
        super("addShippingAddressId");
        this.addressId = addressId;
        this.addressKey = addressKey;
    }

    public static AddShippingAddressId of(final String addressId) {
        return new AddShippingAddressId(addressId, null);
    }

    public static AddShippingAddressId ofKey(final String addressKey) {
        return new AddShippingAddressId(null, addressKey);
    }

    public String getAddressId() {
        return addressId;
    }

    public String getAddressKey() {return addressKey; }
}
