package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.stores.Store;

import javax.annotation.Nullable;

/**
 * Sets the Store.
 *
 * {@doc.gen intro}
 *
 */
public final class SetStore extends UpdateActionImpl<ShoppingList> {
    @Nullable
    private final ResourceIdentifier<Store> store;

    private SetStore(@Nullable final ResourceIdentifier<Store> store) {
        super("setStore");
        this.store = store;
    }

    public static SetStore of(@Nullable final ResourceIdentifier<Store> store) {
        return new SetStore(store);
    }

    @Nullable
    public ResourceIdentifier<Store> getStore() {
        return store;
    }
}
