package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.commands.*;
import io.sphere.sdk.models.Versioned;

import java.util.List;

final class CartUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<Cart, CartUpdateCommand, CartExpansionModel<Cart>> implements CartUpdateCommand {
    CartUpdateCommandImpl(final Versioned<Cart> versioned, final List<? extends UpdateAction<Cart>> updateActions) {
        super(versioned, updateActions, CartEndpoint.ENDPOINT, CartUpdateCommandImpl::new, CartExpansionModel.of());
    }

    CartUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<Cart, CartUpdateCommand, CartExpansionModel<Cart>> builder) {
        super(builder);
    }
}
