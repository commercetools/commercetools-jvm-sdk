package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountDraft;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;

/**
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountCreateCommandTest#execution()}
 */
public interface CartDiscountCreateCommand extends CreateCommand<CartDiscount>, MetaModelExpansionDsl<CartDiscount, CartDiscountCreateCommand, CartDiscountExpansionModel<CartDiscount>> {
    static CartDiscountCreateCommand of(final CartDiscountDraft draft) {
        return new CartDiscountCreateCommandImpl(draft);
    }
}
