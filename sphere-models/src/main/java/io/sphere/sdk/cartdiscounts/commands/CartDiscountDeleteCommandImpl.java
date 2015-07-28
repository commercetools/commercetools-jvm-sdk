package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;

public class CartDiscountDeleteCommandImpl extends ByIdDeleteCommandImpl<CartDiscount> {
    private CartDiscountDeleteCommandImpl(final Versioned<CartDiscount> versioned) {
        super(versioned, CartDiscountEndpoint.ENDPOINT);
    }

    public static DeleteCommand<CartDiscount> of(final Versioned<CartDiscount> versioned) {
        return new CartDiscountDeleteCommandImpl(versioned);
    }
}
