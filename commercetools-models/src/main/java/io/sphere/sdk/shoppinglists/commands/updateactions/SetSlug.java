package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.shoppinglists.ShoppingList;

/**
 * Sets the slug of the shopping list.
 */
public final class SetSlug extends UpdateActionImpl<ShoppingList> {
    private final LocalizedString slug;

    private SetSlug(final LocalizedString slug) {
        super("setSlug");
        this.slug = slug;
    }

    public static SetSlug of(final LocalizedString slug) {
        return new SetSlug(slug);
    }

    public LocalizedString getSlug() {
        return slug;
    }
}