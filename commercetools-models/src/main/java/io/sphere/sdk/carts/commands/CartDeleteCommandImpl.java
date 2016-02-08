package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;

final class CartDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<Cart, CartDeleteCommand, CartExpansionModel<Cart>> implements CartDeleteCommand {
    CartDeleteCommandImpl(final Versioned<Cart> versioned) {
        super(versioned, CartEndpoint.ENDPOINT, CartExpansionModel.of(), CartDeleteCommandImpl::new);
    }

    CartDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<Cart, CartDeleteCommand, CartExpansionModel<Cart>> builder) {
        super(builder);
    }
}
