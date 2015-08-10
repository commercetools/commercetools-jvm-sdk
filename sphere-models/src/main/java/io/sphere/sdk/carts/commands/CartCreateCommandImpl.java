package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;

final class CartCreateCommandImpl extends MetaModelCreateCommandImpl<Cart, CartCreateCommand, CartDraft, CartExpansionModel<Cart>> implements CartCreateCommand {
    CartCreateCommandImpl(final MetaModelCreateCommandBuilder<Cart, CartCreateCommand, CartDraft, CartExpansionModel<Cart>> builder) {
        super(builder);
    }

    CartCreateCommandImpl(final CartDraft body) {
        super(body, CartEndpoint.ENDPOINT, CartExpansionModel.of(), CartCreateCommandImpl::new);
    }
}
