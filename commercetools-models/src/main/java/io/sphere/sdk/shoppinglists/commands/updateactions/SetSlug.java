package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.shoppinglists.ShoppingList;

/**
 * Sets the slug of the shopping list.
 */
public final class SetSlug extends UpdateActionImpl<ShoppingList> {
    private final LocalizedString note;

    private SetSlug(final LocalizedString note) {
        super("setSlug");
        this.note = note;
    }

    public static SetSlug of(final LocalizedString name) {
        return new SetSlug(name);
    }

    public LocalizedString getNote() {
        return note;
    }
}