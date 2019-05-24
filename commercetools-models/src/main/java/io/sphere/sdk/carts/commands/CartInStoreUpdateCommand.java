package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface CartInStoreUpdateCommand extends UpdateCommandDsl<Cart, CartInStoreUpdateCommand>, MetaModelReferenceExpansionDsl<Cart, CartInStoreUpdateCommand, CartExpansionModel<Cart>> {

    static CartInStoreUpdateCommand of(final String storeKey, final Versioned<Cart> versioned, final List<? extends UpdateAction<Cart>> updateActions) {
        return new CartInStoreUpdateCommandImpl(storeKey, versioned, updateActions);
    }

    @SafeVarargs
    static CartInStoreUpdateCommand of(final String storeKey, final Versioned<Cart> versioned, final UpdateAction<Cart> updateAction, final UpdateAction<Cart>... updateActions) {
        final List<UpdateAction<Cart>> actions = new ArrayList<>();
        actions.add(updateAction);
        actions.addAll(Arrays.asList(updateActions));
        return new CartInStoreUpdateCommandImpl(storeKey, versioned, actions);
    }
    
}
