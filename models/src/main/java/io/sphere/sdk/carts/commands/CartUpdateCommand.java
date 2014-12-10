package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public class CartUpdateCommand extends UpdateCommandDslImpl<Cart> {
    private CartUpdateCommand(final Versioned<Cart> versioned, final List<UpdateAction<Cart>> updateActions) {
        super(versioned, updateActions, CartsEndpoint.ENDPOINT);
    }

    public static CartUpdateCommand of(final Versioned<Cart> versioned, final List<UpdateAction<Cart>> updateActions) {
        return new CartUpdateCommand(versioned, updateActions);
    }

    public static CartUpdateCommand of(final Versioned<Cart> versioned, final UpdateAction<Cart> updateAction) {
        return of(versioned, asList(updateAction));
    }
}
