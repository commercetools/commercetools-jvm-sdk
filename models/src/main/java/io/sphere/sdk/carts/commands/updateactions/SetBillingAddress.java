package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Address;

import java.util.Optional;

/**


 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandTest#setBillingAddress()}

 @see io.sphere.sdk.carts.commands.updateactions.SetShippingAddress
 */
public class SetBillingAddress extends UpdateAction<Cart> {
    private final Optional<Address> address;

    private SetBillingAddress(final Optional<Address> address) {
        super("setBillingAddress");
        this.address = address;
    }

    public static SetBillingAddress of(final Optional<Address> address) {
        return new SetBillingAddress(address);
    }

    public static SetBillingAddress of(final Address address) {
        return of(Optional.of(address));
    }

    public Optional<Address> getAddress() {
        return address;
    }
}
