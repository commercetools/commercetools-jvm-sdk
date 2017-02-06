package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.shoppinglists.ShoppingList;

/**
 * Sets the description of the shopping list.
 */
public final class SetDescription extends UpdateActionImpl<ShoppingList> {
    private final LocalizedString description;

    private SetDescription(final LocalizedString description) {
        super("setDescription");
        this.description = description;
    }

    public static SetDescription of(final LocalizedString description) {
        return new SetDescription(description);
    }

    public LocalizedString getDescription() {
        return description;
    }
}