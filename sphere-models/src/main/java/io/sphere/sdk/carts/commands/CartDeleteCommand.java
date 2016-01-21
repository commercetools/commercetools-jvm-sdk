package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;

/**
 * Deletes a cart in SPHERE.IO.
 *
 */
public interface CartDeleteCommand extends MetaModelReferenceExpansionDsl<Cart, CartDeleteCommand, CartExpansionModel<Cart>>, DeleteCommand<Cart> {

    static CartDeleteCommand of(final Versioned<Cart> versioned) {
        return new CartDeleteCommandImpl(versioned);
    }
}
