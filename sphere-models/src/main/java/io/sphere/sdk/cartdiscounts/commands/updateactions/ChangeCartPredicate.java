package io.sphere.sdk.cartdiscounts.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartPredicate;
import io.sphere.sdk.commands.UpdateAction;

/**
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommandTest#changeCartPredicate()}
 */
public class ChangeCartPredicate extends UpdateAction<CartDiscount> {
    private final String cartPredicate;

    private ChangeCartPredicate(final String cartPredicate) {
        super("changeCartPredicate");
        this.cartPredicate = cartPredicate;
    }

    public static ChangeCartPredicate of(final CartPredicate cartPredicate) {
        return of(cartPredicate.toSphereCartPredicate());
    }

    public static ChangeCartPredicate of(final String cartPredicate) {
        return new ChangeCartPredicate(cartPredicate);
    }

    public String getCartPredicate() {
        return cartPredicate;
    }
}
