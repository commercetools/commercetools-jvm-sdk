package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;

final class CartDeleteCommandImpl extends ByIdDeleteCommandImpl<Cart> implements CartDeleteCommand {

    CartDeleteCommandImpl(final Versioned<Cart> versioned) {
        super(versioned, CartEndpoint.ENDPOINT);
    }
}
