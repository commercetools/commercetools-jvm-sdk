package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;

import java.util.Collections;
import java.util.List;

/**
 * Updates a cart.
 *
 {@doc.gen list actions}
 */
public interface CartUpdateCommand extends UpdateCommandDsl<Cart, CartUpdateCommand>, MetaModelReferenceExpansionDsl<Cart, CartUpdateCommand, CartExpansionModel<Cart>> {
    static CartUpdateCommand of(final Versioned<Cart> versioned, final List<? extends UpdateAction<Cart>> updateActions) {
        return new CartUpdateCommandImpl(versioned, updateActions);
    }

    static CartUpdateCommand of(final Versioned<Cart> versioned, final UpdateAction<Cart> updateAction) {
        return of(versioned, Collections.singletonList(updateAction));
    }
}
