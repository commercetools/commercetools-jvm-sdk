package io.sphere.sdk.cartdiscounts.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartPredicate;
import io.sphere.sdk.commands.UpdateActionImpl;

/**
 * Changes the cart predicate.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommandIntegrationTest#changeCartPredicate()}
 */
public final class ChangeCartPredicate extends UpdateActionImpl<CartDiscount> {
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