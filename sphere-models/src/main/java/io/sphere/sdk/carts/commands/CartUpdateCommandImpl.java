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
public class CartUpdateCommandImpl extends UpdateCommandDslImpl<Cart, CartUpdateCommandImpl> {
    private CartUpdateCommandImpl(final Versioned<Cart> versioned, final List<? extends UpdateAction<Cart>> updateActions) {
        super(versioned, updateActions, CartEndpoint.ENDPOINT);
    }

    public static CartUpdateCommandImpl of(final Versioned<Cart> versioned, final List<? extends UpdateAction<Cart>> updateActions) {
        return new CartUpdateCommandImpl(versioned, updateActions);
    }

    public static CartUpdateCommandImpl of(final Versioned<Cart> versioned, final UpdateAction<Cart> updateAction) {
        return of(versioned, asList(updateAction));
    }
}
