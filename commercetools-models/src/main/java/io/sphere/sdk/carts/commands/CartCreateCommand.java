package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.commands.DraftBasedCreateCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;

/**
 * Creates a cart.
 *
 * {@include.example io.sphere.sdk.carts.commands.CartCreateCommandIntegrationTest#execution()}
 * {@include.example io.sphere.sdk.carts.commands.CartCreateCommandIntegrationTest#fullExample()}
 */
public interface CartCreateCommand extends DraftBasedCreateCommand<Cart, CartDraft>, MetaModelReferenceExpansionDsl<Cart, CartCreateCommand, CartExpansionModel<Cart>> {
    static CartCreateCommand of(final CartDraft draft) {
        return new CartCreateCommandImpl(draft);
    }
}
