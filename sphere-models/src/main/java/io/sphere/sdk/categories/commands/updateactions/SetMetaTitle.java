package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedStrings;

import javax.annotation.Nullable;

/**
 * Sets the SEO attribute title.
 *
 * {@include.example io.sphere.sdk.categories.commands.CategoryUpdateCommandTest#setMetaTitle()}
 */
public class SetMetaTitle extends UpdateAction<Category> {
    @Nullable
    private final LocalizedStrings metaTitle;

    private SetMetaTitle(final LocalizedStrings metaTitle) {
        super("setMetaTitle");
        this.metaTitle = metaTitle;
    }

    public static SetMetaTitle of(@Nullable final LocalizedStrings metaTitle) {
        return new SetMetaTitle(metaTitle);
    }

    @Nullable
    public LocalizedStrings getMetaTitle() {
        return metaTitle;
    }
}
