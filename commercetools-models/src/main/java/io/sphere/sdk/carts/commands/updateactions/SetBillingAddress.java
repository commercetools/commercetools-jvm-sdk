package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Address;

import javax.annotation.Nullable;

/**
  Sets the billing address.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#setBillingAddress()}

 @see io.sphere.sdk.carts.commands.updateactions.SetShippingAddress
 */
public final class SetBillingAddress extends UpdateActionImpl<Cart> {
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
