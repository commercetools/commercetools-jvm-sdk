package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.commands.CreateCommandImpl;

final class CartCreateCommandImpl extends CreateCommandImpl<Cart, CartDraft> implements CartCreateCommand {
    CartCreateCommandImpl(final CartDraft body) {
        super(body, CartEndpoint.ENDPOINT);
    }
}
