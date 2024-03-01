package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;

public class FreezeCart extends UpdateActionImpl<Cart> {
    private FreezeCart() {
        super("freezeCart");
    }

    public static FreezeCart of() {
        return new FreezeCart();
    }
}
