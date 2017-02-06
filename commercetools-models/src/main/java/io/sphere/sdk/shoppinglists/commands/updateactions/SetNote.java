package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.shoppinglists.ShoppingList;

/**
 * Sets the note of the shopping list.
 */
public final class SetNote extends UpdateActionImpl<ShoppingList> {
    private final LocalizedString note;

    private SetNote(final LocalizedString note) {
        super("setNote");
        this.note = note;
    }

    public static SetNote of(final LocalizedString name) {
        return new SetNote(name);
    }

    public LocalizedString getNote() {
        return note;
    }
}