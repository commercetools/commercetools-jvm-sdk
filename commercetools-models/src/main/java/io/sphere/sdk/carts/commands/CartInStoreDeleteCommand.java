package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;

public interface CartInStoreDeleteCommand extends MetaModelReferenceExpansionDsl<Cart, CartInStoreDeleteCommand, CartExpansionModel<Cart>>, DeleteCommand<Cart> {

    static CartInStoreDeleteCommand of(final String storeKey, final Versioned<Cart> versioned) {
        return new CartInStoreDeleteCommandImpl(storeKey, versioned,false);
    }
    
    static CartInStoreDeleteCommand of(final String storeKey, final Versioned<Cart> versioned, final boolean eraseData) {
        return new CartInStoreDeleteCommandImpl(storeKey, versioned,eraseData);
    }
}