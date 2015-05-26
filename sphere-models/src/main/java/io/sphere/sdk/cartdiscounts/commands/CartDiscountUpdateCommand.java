package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public class CartDiscountUpdateCommand extends UpdateCommandDslImpl<CartDiscount> {
    private CartDiscountUpdateCommand(final Versioned<CartDiscount> versioned, final List<? extends UpdateAction<CartDiscount>> updateActions) {
        super(versioned, updateActions, CartDiscountEndpoint.ENDPOINT);
    }

    public static CartDiscountUpdateCommand of(final Versioned<CartDiscount> versioned, final UpdateAction<CartDiscount> updateAction) {
        return of(versioned, asList(updateAction));
    }

    public static CartDiscountUpdateCommand of(final Versioned<CartDiscount> versioned, final List<? extends UpdateAction<CartDiscount>> updateActions) {
        return new CartDiscountUpdateCommand(versioned, updateActions);
    }
}
