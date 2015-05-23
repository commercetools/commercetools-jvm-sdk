package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedStrings;

import java.util.Optional;

/**
 * Sets the SEO attribute title.
 *
 * {@include.example io.sphere.sdk.categories.commands.CategoryUpdateCommandTest#setMetaTitle()}
 */
public class SetMetaTitle extends UpdateAction<Category> {
    private final Optional<LocalizedStrings> metaTitle;

    private SetMetaTitle(final Optional<LocalizedStrings> metaTitle) {
        super("setMetaTitle");
        this.metaTitle = metaTitle;
    }

    public static SetMetaTitle of(final LocalizedStrings metaTitle) {
        return of(Optional.of(metaTitle));
    }

    public static SetMetaTitle of(final Optional<LocalizedStrings> metaTitle) {
        return new SetMetaTitle(metaTitle);
    }

    public Optional<LocalizedStrings> getMetaTitle() {
        return metaTitle;
    }
}
