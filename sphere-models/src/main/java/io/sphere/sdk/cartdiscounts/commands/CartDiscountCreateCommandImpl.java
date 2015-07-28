package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountDraft;
import io.sphere.sdk.commands.CreateCommandImpl;

/**
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountCreateCommandTest#execution()}
 */
public class CartDiscountCreateCommandImpl extends CreateCommandImpl<CartDiscount, CartDiscountDraft> {
    private CartDiscountCreateCommandImpl(final CartDiscountDraft draft) {
        super(draft, CartDiscountEndpoint.ENDPOINT);
    }

    public static CartDiscountCreateCommandImpl of(final CartDiscountDraft draft) {
        return new CartDiscountCreateCommandImpl(draft);
    }
}
