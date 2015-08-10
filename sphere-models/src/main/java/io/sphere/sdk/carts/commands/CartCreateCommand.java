package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;

/**
 * Creates a cart.
 *
 * {@include.example io.sphere.sdk.carts.commands.CartCreateCommandTest#execution()}
 */
public interface CartCreateCommand extends CreateCommand<Cart>, MetaModelExpansionDsl<Cart, CartCreateCommand, CartExpansionModel<Cart>> {
    static CartCreateCommand of(final CartDraft draft) {
        return new CartCreateCommandImpl(draft);
    }
}
