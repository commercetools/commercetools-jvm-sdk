package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.shoppinglists.ShoppingList;

import javax.annotation.Nullable;

/**
 * Sets the TTL {@link ShoppingList#getDeleteDaysAfterLastModification()} of the shopping list.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommandIntegrationTest#setDeleteDaysAfterLastModification()}
 *
 * @see ShoppingList#getDeleteDaysAfterLastModification()
 */
public final class SetDeleteDaysAfterLastModification extends UpdateActionImpl<ShoppingList> {
    @Nullable
    private final Integer deleteDaysAfterLastModification;

    private SetDeleteDaysAfterLastModification(@Nullable final Integer deleteDaysAfterLastModification) {
        super("setDeleteDaysAfterLastModification");
        this.deleteDaysAfterLastModification = deleteDaysAfterLastModification;
    }

    public static SetDeleteDaysAfterLastModification of(@Nullable final Integer deleteDaysAfterLastModification) {
        return new SetDeleteDaysAfterLastModification(deleteDaysAfterLastModification);
    }

    public static SetDeleteDaysAfterLastModification ofUnset() {
        return new SetDeleteDaysAfterLastModification(null);
    }

    @Nullable
    public Integer getDeleteDaysAfterLastModification() {
        return deleteDaysAfterLastModification;
    }
}
