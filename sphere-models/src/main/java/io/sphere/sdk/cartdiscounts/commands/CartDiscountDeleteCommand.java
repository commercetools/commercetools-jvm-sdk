package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;

public interface CartDiscountDeleteCommand extends ByIdDeleteCommand<CartDiscount>, MetaModelExpansionDsl<CartDiscount, CartDiscountDeleteCommand, CartDiscountExpansionModel<CartDiscount>> {
    static CartDiscountDeleteCommand of(final Versioned<CartDiscount> versioned) {
        return new CartDiscountDeleteCommandImpl(versioned);
    }
}
