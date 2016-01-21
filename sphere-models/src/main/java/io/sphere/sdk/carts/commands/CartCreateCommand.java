package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;

/**
 * Creates a cart.
 *
 * {@include.example io.sphere.sdk.carts.commands.CartCreateCommandTest#execution()}
 * {@include.example io.sphere.sdk.carts.commands.CartCreateCommandTest#fullExample()}
 */
public interface CartCreateCommand extends CreateCommand<Cart>, MetaModelReferenceExpansionDsl<Cart, CartCreateCommand, CartExpansionModel<Cart>> {
    static CartCreateCommand of(final CartDraft draft) {
        return new CartCreateCommandImpl(draft);
    }
}
