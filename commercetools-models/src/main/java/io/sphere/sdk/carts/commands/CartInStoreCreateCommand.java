package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.commands.DraftBasedCreateCommandDsl;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;

public interface CartInStoreCreateCommand extends DraftBasedCreateCommandDsl<Cart, CartDraft, CartInStoreCreateCommand>, MetaModelReferenceExpansionDsl<Cart, CartInStoreCreateCommand, CartExpansionModel<Cart>> {

    static CartInStoreCreateCommand of(final String storeKey, final CartDraft draft) {
        return new CartInStoreCreateCommandImpl(storeKey, draft);
    }
    
}
