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
    
    /**
     Creates a command object to delete a {@link Cart} by ID.
     @param versioned the object to delete (so directly a {@link Cart}) or just the version/ID information of it
     @param eraseData  If set to {@literal true}, the commercetools platform guarantees that all personal data related to the particular object, including invisible data, is erased, in compliance with the GDPR.
     @return delete command
     */
    static CartInStoreDeleteCommand of(final String storeKey, final Versioned<Cart> versioned, final boolean eraseData) {
        return new CartInStoreDeleteCommandImpl(storeKey, versioned,eraseData);
    }
}