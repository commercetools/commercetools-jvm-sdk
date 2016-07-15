package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.messages.OrderShippingAddressSetMessage;

import javax.annotation.Nullable;

/**
 * Sets the shipping address of an order (not its cart) to a new value or to null.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#setShippingAddress()}
 *
 * @see Order#getShippingAddress()
 * @see OrderShippingAddressSetMessage
 */
public final class SetShippingAddress extends UpdateActionImpl<Order> {
    @Nullable
    private final Address address;

    private SetShippingAddress(@Nullable final Address address) {
        super("setShippingAddress");
        this.address = address;
    }

    public static SetShippingAddress of(@Nullable final Address address) {
        return new SetShippingAddress(address);
    }

    @Nullable
    public Address getAddress() {
        return address;
    }
}
