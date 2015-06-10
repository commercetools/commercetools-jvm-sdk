package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.commands.CreateCommandImpl;

/**

 Creates a cart.

 {@include.example io.sphere.sdk.carts.commands.CartCreateCommandTest#execution()}


 */
public class CartCreateCommand extends CreateCommandImpl<Cart, CartDraft> {
    private CartCreateCommand(final CartDraft body) {
        super(body, CartEndpoint.ENDPOINT);
    }

    public static CartCreateCommand of(final CartDraft draft) {
        return new CartCreateCommand(draft);
    }
}
