package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedStrings;

import java.util.Optional;

/**
 * Sets the SEO attribute description.
 *
 * {@include.example io.sphere.sdk.categories.commands.CategoryUpdateCommandTest#setMetaDescription()}
 */
public class SetMetaDescription extends UpdateAction<Category> {
    private final Optional<LocalizedStrings> metaDescription;

    private SetMetaDescription(final Optional<LocalizedStrings> metaDescription) {
        super("setMetaDescription");
        this.metaDescription = metaDescription;
    }

    public static SetMetaDescription of(final LocalizedStrings metaDescription) {
        return of(Optional.of(metaDescription));
    }

    public static SetMetaDescription of(final Optional<LocalizedStrings> metaDescription) {
        return new SetMetaDescription(metaDescription);
    }

    public Optional<LocalizedStrings> getMetaDescription() {
        return metaDescription;
    }
}
