package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.expansion.ShoppingListExpansionModel;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

public interface ShoppingListInStoreDeleteCommand extends MetaModelReferenceExpansionDsl<ShoppingList, ShoppingListInStoreDeleteCommand, ShoppingListExpansionModel<ShoppingList>>, DeleteCommand<ShoppingList>  {

    static ShoppingListInStoreDeleteCommand of(final String storeKey, final Versioned<ShoppingList> versioned) {
        return new ShoppingListInStoreDeleteCommandImpl(storeKey, versioned,false);
    }
    
    static ShoppingListInStoreDeleteCommand of(final String storeKey, final Versioned<ShoppingList> versioned, final boolean eraseData) {
        return new ShoppingListInStoreDeleteCommandImpl(storeKey, versioned, eraseData);
    }

    static ShoppingListInStoreDeleteCommand ofKey(final String storeKey, final String key, final Long version) {
        final Versioned<ShoppingList> versioned = Versioned.of("key=" + urlEncode(key), version);//hack for simple reuse
        return of(storeKey, versioned);
    }
    
    static ShoppingListInStoreDeleteCommand ofKey(final String storeKey, final String key, final Long version, final boolean eraseData) {
        final Versioned<ShoppingList> versioned = Versioned.of("key=" + urlEncode(key), version);//hack for simple reuse
        return of(storeKey, versioned, eraseData);
    }
}