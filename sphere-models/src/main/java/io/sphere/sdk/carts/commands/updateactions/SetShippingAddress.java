package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Address;

import java.util.Optional;

/**
 Sets the shipping address of the cart.
 Setting the shipping address also sets the tax rates of the line items and calculates the taxed price.
 If the address is not provided, the shipping address is unset, the taxedPrice is unset and the taxRates are unset in all line items.

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandTest#setShippingAddress()}
 */
public class SetShippingAddress extends UpdateAction<Cart> {
    private final Optional<Address> address;

    private SetShippingAddress(final Optional<Address> address) {
        super("setShippingAddress");
        this.address = address;
    }

    public static SetShippingAddress of(final Optional<Address> address) {
        return new SetShippingAddress(address);
    }

    public static SetShippingAddress of(final Address address) {
        return of(Optional.of(address));
    }

    public Optional<Address> getAddress() {
        return address;
    }
}
