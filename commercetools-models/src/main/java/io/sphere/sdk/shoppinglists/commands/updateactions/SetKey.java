package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.shoppinglists.ShoppingList;

import javax.annotation.Nullable;

/**
 * Sets the key of the shopping list.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommandIntegrationTest#setKey()}
 *
 * @see ShoppingList#getKey()
 */
public final class SetKey extends UpdateActionImpl<ShoppingList> {
    @Nullable
    private final String key;

    private SetKey(@Nullable final String key) {
        super("setKey");
        this.key = key;
    }

    public static SetKey of(@Nullable final String key) {
        return new SetKey(key);
    }

    public static SetKey ofUnset() {
        return new SetKey(null);
    }

    public String getKey() {
        return key;
    }
}