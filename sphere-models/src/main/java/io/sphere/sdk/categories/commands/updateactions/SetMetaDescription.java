package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedStrings;

import javax.annotation.Nullable;

/**
 * Sets the SEO attribute description.
 *
 * {@include.example io.sphere.sdk.categories.commands.CategoryUpdateCommandTest#setMetaDescription()}
 */
public class SetMetaDescription extends UpdateAction<Category> {
    @Nullable
    private final LocalizedStrings metaDescription;

    private SetMetaDescription(final LocalizedStrings metaDescription) {
        super("setMetaDescription");
        this.metaDescription = metaDescription;
    }

    public static SetMetaDescription of(@Nullable final LocalizedStrings metaDescription) {
        return new SetMetaDescription(metaDescription);
    }

    @Nullable
    public LocalizedStrings getMetaDescription() {
        return metaDescription;
    }
}
