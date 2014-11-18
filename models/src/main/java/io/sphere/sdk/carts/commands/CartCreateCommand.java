package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.commands.CreateCommandImpl;

/**

 Creates a cart.

 {@include.example io.sphere.sdk.carts.CartIntegrationTest#create()}


 */
public class CartCreateCommand extends CreateCommandImpl<Cart, CartDraft> {
    public CartCreateCommand(final CartDraft body) {
        super(body, CartsEndpoint.ENDPOINT);
    }
}
