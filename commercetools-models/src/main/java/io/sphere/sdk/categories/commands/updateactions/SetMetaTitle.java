package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;

/**
 * Sets the SEO attribute title.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.categories.commands.CategoryUpdateCommandIntegrationTest#setMetaTitle()}
 */
public final class SetMetaTitle extends UpdateActionImpl<Category> {
    @Nullable
    private final LocalizedString metaTitle;

    private SetMetaTitle(@Nullable final LocalizedString metaTitle) {
        super("setMetaTitle");
        this.metaTitle = metaTitle;
    }

    public static SetMetaTitle of(@Nullable final LocalizedString metaTitle) {
        return new SetMetaTitle(metaTitle);
    }

    @Nullable
    public LocalizedString getMetaTitle() {
        return metaTitle;
    }
}
