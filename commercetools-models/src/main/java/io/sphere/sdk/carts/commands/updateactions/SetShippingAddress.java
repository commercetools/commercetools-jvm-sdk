package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Address;

import javax.annotation.Nullable;

/**
 Sets the shipping address of the cart.
 Setting the shipping address also sets the tax rates of the line items and calculates the taxed price.
 If the address is not provided, the shipping address is unset, the taxedPrice is unset and the taxRates are unset in all line items.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#setShippingAddress()}
 */
public final class SetShippingAddress extends UpdateActionImpl<Cart> {
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
