package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountDraft;
import io.sphere.sdk.commands.CreateCommandImpl;

/**
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountCreateCommandTest#execution()}
 */
public class CartDiscountCreateCommand extends CreateCommandImpl<CartDiscount, CartDiscountDraft> {
    private CartDiscountCreateCommand(final CartDiscountDraft body) {
        super(body, CartDiscountEndpoint.ENDPOINT);
    }

    public static CartDiscountCreateCommand of(final CartDiscountDraft draft) {
        return new CartDiscountCreateCommand(draft);
    }
}
