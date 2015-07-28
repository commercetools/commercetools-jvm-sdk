package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.commands.CreateCommandImpl;

/**

 Creates a cart.

 {@include.example io.sphere.sdk.carts.commands.CartCreateCommandTest#execution()}


 */
public class CartCreateCommandImpl extends CreateCommandImpl<Cart, CartDraft> {
    private CartCreateCommandImpl(final CartDraft body) {
        super(body, CartEndpoint.ENDPOINT);
    }

    public static CartCreateCommandImpl of(final CartDraft draft) {
        return new CartCreateCommandImpl(draft);
    }
}
