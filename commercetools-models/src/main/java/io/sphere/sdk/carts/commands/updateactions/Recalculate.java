package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;

/**
 Updates tax rates and prices.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#recalculate()}
 */
public final class Recalculate extends UpdateActionImpl<Cart> {
    private Recalculate() {
        super("recalculate");
    }

    public static Recalculate of() {
        return new Recalculate();
    }
}
