package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.orders.Order;

import javax.annotation.Nullable;

/**
  Sets the billing address.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#setBillingAddress()}

 @see io.sphere.sdk.carts.commands.updateactions.SetShippingAddress
 */
public final class SetBillingAddress extends UpdateActionImpl<Order> {
    @Nullable
    private final Address address;

    private SetBillingAddress(@Nullable final Address address) {
        super("setBillingAddress");
        this.address = address;
    }

    public static SetBillingAddress of(@Nullable final Address address) {
        return new SetBillingAddress(address);
    }

    @Nullable
    public Address getAddress() {
        return address;
    }
}
