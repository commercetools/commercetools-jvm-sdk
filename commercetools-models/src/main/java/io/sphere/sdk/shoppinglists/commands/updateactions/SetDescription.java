package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.shoppinglists.ShoppingList;

import javax.annotation.Nullable;

/**
 * Sets the description of the shopping list.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommandIntegrationTest#setDescription()}
 *
 * @see ShoppingList#getDescription()
 */
public final class SetDescription extends UpdateActionImpl<ShoppingList> {
    @Nullable
    private final LocalizedString description;

    private SetDescription(final LocalizedString description) {
        super("setDescription");
        this.description = description;
    }

    public static SetDescription of(@Nullable final LocalizedString description) {
        return new SetDescription(description);
    }

    @Nullable
    public LocalizedString getDescription() {
        return description;
    }
}