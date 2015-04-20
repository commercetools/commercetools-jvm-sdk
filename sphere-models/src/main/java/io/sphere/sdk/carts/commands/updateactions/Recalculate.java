package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateAction;

/**
 Updates tax rates and prices.

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandTest#recalculate()}
 */
public class Recalculate extends UpdateAction<Cart> {
    private Recalculate() {
        super("recalculate");
    }

    public static Recalculate of() {
        return new Recalculate();
    }
}
