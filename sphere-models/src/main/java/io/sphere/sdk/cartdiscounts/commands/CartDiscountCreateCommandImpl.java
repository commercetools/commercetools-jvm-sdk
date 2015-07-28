package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountDraft;
import io.sphere.sdk.commands.CreateCommandImpl;

final class CartDiscountCreateCommandImpl extends CreateCommandImpl<CartDiscount, CartDiscountDraft> implements CartDiscountCreateCommand {
    CartDiscountCreateCommandImpl(final CartDiscountDraft draft) {
        super(draft, CartDiscountEndpoint.ENDPOINT);
    }
}
