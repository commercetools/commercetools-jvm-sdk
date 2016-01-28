package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
        import io.sphere.sdk.models.Versioned;

/**
 * Deletes a {@link CartDiscount}.
 */
public interface CartDiscountDeleteCommand extends MetaModelReferenceExpansionDsl<CartDiscount, CartDiscountDeleteCommand, CartDiscountExpansionModel<CartDiscount>>, DeleteCommand<CartDiscount> {
    static CartDiscountDeleteCommand of(final Versioned<CartDiscount> versioned) {
        return new CartDiscountDeleteCommandImpl(versioned);
    }
}
