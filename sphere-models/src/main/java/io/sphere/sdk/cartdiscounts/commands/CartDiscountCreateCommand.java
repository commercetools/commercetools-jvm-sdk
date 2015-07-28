package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountDraft;
import io.sphere.sdk.commands.CreateCommand;

/**
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountCreateCommandTest#execution()}
 */
public interface CartDiscountCreateCommand extends CreateCommand<CartDiscount> {
    static CartDiscountCreateCommand of(final CartDiscountDraft draft) {
        return new CartDiscountCreateCommandImpl(draft);
    }
}
