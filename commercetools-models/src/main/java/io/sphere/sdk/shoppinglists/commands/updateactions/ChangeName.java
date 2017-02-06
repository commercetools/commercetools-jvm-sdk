package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;

import io.sphere.sdk.shoppinglists.ShoppingList;

/**
 * Changes the name of the shopping list.
 */
public final class ChangeName extends UpdateActionImpl<ShoppingList> {
    private final LocalizedString name;

    private ChangeName(final LocalizedString name) {
        super("changeName");
        this.name = name;
    }

    public static io.sphere.sdk.shoppinglists.commands.updateactions.ChangeName of(final LocalizedString name) {
        return new io.sphere.sdk.shoppinglists.commands.updateactions.ChangeName(name);
    }

    public LocalizedString getName() {
        return name;
    }
}