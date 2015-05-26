package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedStrings;

import java.util.Optional;

/**
 * Sets the SEO attribute keywords.
 * {@include.example io.sphere.sdk.categories.commands.CategoryUpdateCommandTest#setMetaKeywords()}
 */
public class SetMetaKeywords extends UpdateAction<Category> {
    private final Optional<LocalizedStrings> metaKeywords;

    private SetMetaKeywords(final Optional<LocalizedStrings> metaKeywords) {
        super("setMetaKeywords");
        this.metaKeywords = metaKeywords;
    }

    public static SetMetaKeywords of(final LocalizedStrings metaKeywords) {
        return of(Optional.of(metaKeywords));
    }

    public static SetMetaKeywords of(final Optional<LocalizedStrings> metaKeywords) {
        return new SetMetaKeywords(metaKeywords);
    }

    public Optional<LocalizedStrings> getMetaKeywords() {
        return metaKeywords;
    }
}
