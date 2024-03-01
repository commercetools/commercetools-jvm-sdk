package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;

public final class UnfreezeCart extends UpdateActionImpl<Cart> {
    private UnfreezeCart() {
        super("unfreezeCart");
    }

    public static UnfreezeCart of() {
        return new UnfreezeCart();
    }
}
