package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.shoppinglists.ShoppingList;

import javax.annotation.Nullable;

/**
 * Sets the slug of the shopping list.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommandIntegrationTest#setSlug()}
 *
 * @see ShoppingList#getSlug()
 */
public final class SetSlug extends UpdateActionImpl<ShoppingList> {
    @Nullable
    private final LocalizedString slug;

    private SetSlug(@Nullable final LocalizedString slug) {
        super("setSlug");
        this.slug = slug;
    }

    public static SetSlug of(@Nullable final LocalizedString slug) {
        return new SetSlug(slug);
    }

    public static SetSlug ofUnset() {
        return new SetSlug(null);
    }

    @Nullable
    public LocalizedString getSlug() {
        return slug;
    }
}