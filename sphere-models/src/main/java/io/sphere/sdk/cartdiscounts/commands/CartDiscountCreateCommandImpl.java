package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountDraft;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandBuilder;
import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandImpl;

final class CartDiscountCreateCommandImpl extends ReferenceExpandeableCreateCommandImpl<CartDiscount, CartDiscountCreateCommand, CartDiscountDraft, CartDiscountExpansionModel<CartDiscount>> implements CartDiscountCreateCommand {
    CartDiscountCreateCommandImpl(final ReferenceExpandeableCreateCommandBuilder<CartDiscount, CartDiscountCreateCommand, CartDiscountDraft, CartDiscountExpansionModel<CartDiscount>> builder) {
        super(builder);
    }

    CartDiscountCreateCommandImpl(final CartDiscountDraft draft) {
        super(draft, CartDiscountEndpoint.ENDPOINT, CartDiscountExpansionModel.of(), CartDiscountCreateCommandImpl::new);
    }
}
