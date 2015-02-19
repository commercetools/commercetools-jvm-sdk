package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.DeleteByIdCommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;

/**
 * Deletes a cart in SPHERE.IO.
 *
 */
public final class CartDeleteByIdCommand extends DeleteByIdCommandImpl<Cart> {

    private CartDeleteByIdCommand(final Versioned<Cart> versioned) {
        super(versioned, CartsEndpoint.ENDPOINT);
    }

    public static DeleteCommand<Cart> of(final Versioned<Cart> versioned) {
        return new CartDeleteByIdCommand(versioned);
    }
}
