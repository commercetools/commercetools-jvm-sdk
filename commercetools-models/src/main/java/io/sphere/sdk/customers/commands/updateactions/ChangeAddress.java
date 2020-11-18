package io.sphere.sdk.customers.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.orders.Delivery;
import io.sphere.sdk.orders.DeliveryItem;
import io.sphere.sdk.orders.ParcelDraft;
import io.sphere.sdk.orders.commands.updateactions.AddParcelToDelivery;

import javax.annotation.Nullable;
import java.util.List;

import static java.lang.String.format;

/**
 * Replaces the address with the given ID, with the new address in the customer's addresses array. The new address will have the same ID.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerUpdateCommandIntegrationTest#changeAddress()}
 *
 * @see Customer
 */
public final class ChangeAddress extends UpdateActionImpl<Customer> {
    private final Address address;
    @Nullable
    private final String addressId;
    @Nullable
    private final String addressKey;

    private ChangeAddress(@Nullable final String addressId, final Address address, @Nullable final String addressKey) {
        super("changeAddress");
        this.address = address;
        this.addressId = addressId;
        this.addressKey = addressKey;
    }

    public static ChangeAddress of(final String addressId, final Address address) {
        return new ChangeAddress(addressId, address, null);
    }

    public static ChangeAddress of(final Address address, final String addressKey) {
        return new ChangeAddress(null, address, addressKey);
    }

    public static ChangeAddress ofOldAddressToNewAddress(final Address oldAddress, final Address newAddress) {
        if (oldAddress.getId() == null && oldAddress.getKey() == null) {
            throw new IllegalArgumentException(format("The address %s should have an id or a key.", oldAddress));
        }
        if (oldAddress.getId() != null) {
            return of(oldAddress.getId(), newAddress);
        } else {
            return of(newAddress, oldAddress.getKey());
        }
    }

    public Address getAddress() {
        return address;
    }

    public String getAddressId() {
        return addressId;
    }

    public String getAddressKey() {
        return addressKey;
    }
}
