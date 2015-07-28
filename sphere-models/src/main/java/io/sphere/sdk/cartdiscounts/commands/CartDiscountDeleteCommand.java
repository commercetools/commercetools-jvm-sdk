package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;

public interface CartDiscountDeleteCommand extends ByIdDeleteCommand<CartDiscount> {
    static DeleteCommand<CartDiscount> of(final Versioned<CartDiscount> versioned) {
        return new CartDiscountDeleteCommandImpl(versioned);
    }
}
