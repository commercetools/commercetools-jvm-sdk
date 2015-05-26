package io.sphere.sdk.discountcodes.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartPredicate;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.discountcodes.DiscountCode;

import java.util.Optional;

/**
 * {@include.example io.sphere.sdk.discountcodes.commands.DiscountCodeUpdateCommandTest#setCartPredicate()}
 */
public class SetCartPredicate extends UpdateAction<DiscountCode> {
    private final Optional<String> cartPredicate;

    private SetCartPredicate(final Optional<String> cartPredicate) {
        super("setCartPredicate");
        this.cartPredicate = cartPredicate;
    }

    public static SetCartPredicate of(final CartPredicate cartPredicate) {
        return of(Optional.of(cartPredicate));
    }

    public static SetCartPredicate of(final Optional<CartPredicate> cartPredicate) {
        return new SetCartPredicate(cartPredicate.map(CartPredicate::toSphereCartPredicate));
    }

    public Optional<String> getCartPredicate() {
        return cartPredicate;
    }
}
