package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;

/**
 * Changes the slug of a category.
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.categories.commands.CategoryUpdateCommandIntegrationTest#changeSlug()}
 */
public final class ChangeSlug extends UpdateActionImpl<Category> {
    private final LocalizedString slug;

    private ChangeSlug(final LocalizedString slug) {
        super("changeSlug");
        this.slug = slug;
    }

    public static ChangeSlug of(final LocalizedString slug) {
        return new ChangeSlug(slug);
    }

    public LocalizedString getSlug() {
        return slug;
    }
}