package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;

/**
 * Sets the SEO attribute keywords.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.categories.commands.CategoryUpdateCommandIntegrationTest#setMetaKeywords()}
 */
public final class SetMetaKeywords extends UpdateActionImpl<Category> {
    @Nullable
    private final LocalizedString metaKeywords;

    private SetMetaKeywords(@Nullable final LocalizedString metaKeywords) {
        super("setMetaKeywords");
        this.metaKeywords = metaKeywords;
    }

    public static SetMetaKeywords of(@Nullable final LocalizedString metaKeywords) {
        return new SetMetaKeywords(metaKeywords);
    }

    @Nullable
    public LocalizedString getMetaKeywords() {
        return metaKeywords;
    }
}
