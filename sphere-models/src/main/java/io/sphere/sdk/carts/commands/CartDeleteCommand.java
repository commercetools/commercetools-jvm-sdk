package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;

/**
 * Deletes a cart in SPHERE.IO.
 *
 */
public interface CartDeleteCommand extends ByIdDeleteCommand<Cart>, MetaModelExpansionDsl<Cart, CartDeleteCommand, CartExpansionModel<Cart>> {

    static CartDeleteCommand of(final Versioned<Cart> versioned) {
        return new CartDeleteCommandImpl(versioned);
    }
}
