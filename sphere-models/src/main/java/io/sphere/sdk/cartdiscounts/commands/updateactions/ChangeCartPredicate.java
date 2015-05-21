package io.sphere.sdk.cartdiscounts.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountPredicate;
import io.sphere.sdk.commands.UpdateAction;

public class ChangeCartPredicate extends UpdateAction<CartDiscount> {
    private final String cartPredicate;

    private ChangeCartPredicate(final String cartPredicate) {
        super("changeCartPredicate");
        this.cartPredicate = cartPredicate;
    }

    public static ChangeCartPredicate of(final CartDiscountPredicate cartPredicate) {
        return of(cartPredicate.toSphereCartDiscountPredicate());
    }

    public static ChangeCartPredicate of(final String cartPredicate) {
        return new ChangeCartPredicate(cartPredicate);
    }

    public String getCartPredicate() {
        return cartPredicate;
    }
}
