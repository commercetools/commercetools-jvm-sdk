package io.sphere.sdk.shoppinglists.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.shoppinglists.expansion.ShoppingListExpansionModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

public interface ShoppingListInStoreUpdateCommand extends UpdateCommandDsl<ShoppingList, ShoppingListInStoreUpdateCommand>, MetaModelReferenceExpansionDsl<ShoppingList, ShoppingListInStoreUpdateCommand, ShoppingListExpansionModel<ShoppingList>> {
    
    static ShoppingListInStoreUpdateCommand of(final Versioned<ShoppingList> versioned, final String storeKey, final List<? extends UpdateAction<ShoppingList>> updateActions) {
        return new ShoppingListInStoreUpdateCommandImpl(versioned, storeKey, updateActions);
    }

    @SafeVarargs
    static ShoppingListInStoreUpdateCommand of(final Versioned<ShoppingList> versioned, final String storeKey, final UpdateAction<ShoppingList> updateAction, final UpdateAction<ShoppingList>... updateActions) {
        final List<UpdateAction<ShoppingList>> actions = new ArrayList<>();
        actions.add(updateAction);
        actions.addAll(Arrays.asList(updateActions));
        return new ShoppingListInStoreUpdateCommandImpl(versioned, storeKey, actions);
    }

    static ShoppingListInStoreUpdateCommand ofKey(final String key, final Long version, final String storeKey, final List<? extends UpdateAction<ShoppingList>> updateActions) {
        final Versioned<ShoppingList> versioned = Versioned.of("key=" + urlEncode(key), version);
        return new ShoppingListInStoreUpdateCommandImpl(versioned, storeKey, updateActions);
    }

    static ShoppingListInStoreUpdateCommand ofKey(final String key, final Long version, final String storeKey, final UpdateAction<ShoppingList> updateAction) {
        return ofKey(key, version, storeKey, Collections.singletonList(updateAction));
    }
}
