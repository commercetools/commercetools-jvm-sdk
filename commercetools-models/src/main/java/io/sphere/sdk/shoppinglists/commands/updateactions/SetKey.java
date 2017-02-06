package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.shoppinglists.ShoppingList;

/**
 * Sets the key of the shopping list.
 */
public final class SetKey extends UpdateActionImpl<ShoppingList> {
    private final String key;

    private SetKey(final String key) {
        super("setKey");
        this.key = key;
    }

    public static SetKey of(final String key) {
        return new SetKey(key);
    }

    public String getKey() {
        return key;
    }
}