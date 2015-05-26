package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;

public class CartDiscountDeleteCommand extends ByIdDeleteCommandImpl<CartDiscount> {
    private CartDiscountDeleteCommand(final Versioned<CartDiscount> versioned) {
        super(versioned, CartDiscountEndpoint.ENDPOINT);
    }

    public static DeleteCommand<CartDiscount> of(final Versioned<CartDiscount> versioned) {
        return new CartDiscountDeleteCommand(versioned);
    }
}
