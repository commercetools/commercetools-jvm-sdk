package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.shoppinglists.ShoppingList;

/**
 * Changes the name of the shopping list.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommandIntegrationTest#changeName()}
 *
 * @see ShoppingList#getName()
 */
public final class ChangeName extends UpdateActionImpl<ShoppingList> {
    private final LocalizedString name;

    private ChangeName(final LocalizedString name) {
        super("changeName");
        this.name = name;
    }

    public static ChangeName of(final LocalizedString name) {
        return new ChangeName(name);
    }

    public LocalizedString getName() {
        return name;
    }
}