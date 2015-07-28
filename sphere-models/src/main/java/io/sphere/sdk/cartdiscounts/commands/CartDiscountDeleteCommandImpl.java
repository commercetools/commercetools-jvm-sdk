package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;

final class CartDiscountDeleteCommandImpl extends ByIdDeleteCommandImpl<CartDiscount> implements CartDiscountDeleteCommand {
    CartDiscountDeleteCommandImpl(final Versioned<CartDiscount> versioned) {
        super(versioned, CartDiscountEndpoint.ENDPOINT);
    }
}
