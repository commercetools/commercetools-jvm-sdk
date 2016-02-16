package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;

/**
 * Changes the name of a category.
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.categories.commands.CategoryUpdateCommandIntegrationTest#changeName()}
 */
public final class ChangeName extends UpdateActionImpl<Category> {
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