package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountDraft;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.commands.DraftBasedCreateCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;

/**
 * Creates a {@link CartDiscount}.
 *
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountCreateCommandTest#execution()}
 */
public interface CartDiscountCreateCommand extends DraftBasedCreateCommand<CartDiscount, CartDiscountDraft>, MetaModelReferenceExpansionDsl<CartDiscount, CartDiscountCreateCommand, CartDiscountExpansionModel<CartDiscount>> {
    static CartDiscountCreateCommand of(final CartDiscountDraft draft) {
        return new CartDiscountCreateCommandImpl(draft);
    }
}
