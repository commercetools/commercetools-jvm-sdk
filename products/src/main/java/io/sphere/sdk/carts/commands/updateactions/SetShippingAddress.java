package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Address;

import java.util.Optional;

/**

 {@include.example io.sphere.sdk.carts.CartIntegrationTest#setShippingAddressUpdateAction()}
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
