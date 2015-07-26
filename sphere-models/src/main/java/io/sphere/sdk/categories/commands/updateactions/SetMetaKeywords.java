package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedStrings;

import javax.annotation.Nullable;

/**
 * Sets the SEO attribute keywords.
 * {@include.example io.sphere.sdk.categories.commands.CategoryUpdateCommandTest#setMetaKeywords()}
 */
public class SetMetaKeywords extends UpdateAction<Category> {
    @Nullable
    private final LocalizedStrings metaKeywords;

    private SetMetaKeywords(@Nullable final LocalizedStrings metaKeywords) {
        super("setMetaKeywords");
        this.metaKeywords = metaKeywords;
    }

    public static SetMetaKeywords of(@Nullable final LocalizedStrings metaKeywords) {
        return new SetMetaKeywords(metaKeywords);
    }

    @Nullable
    public LocalizedStrings getMetaKeywords() {
        return metaKeywords;
    }
}
