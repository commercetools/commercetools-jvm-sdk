package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;

/**
 * Deletes a cart in SPHERE.IO.
 *
 */
public interface CartDeleteCommand extends ByIdDeleteCommand<Cart> {

    static DeleteCommand<Cart> of(final Versioned<Cart> versioned) {
        return new CartDeleteCommandImpl(versioned);
    }
}
