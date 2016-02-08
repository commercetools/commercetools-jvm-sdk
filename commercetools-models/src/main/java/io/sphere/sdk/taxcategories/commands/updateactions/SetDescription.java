package io.sphere.sdk.taxcategories.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.annotation.Nullable;

/**
 * Updates the description of a tax category.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.taxcategories.commands.TaxCategoryUpdateCommandTest#setDescription()}
 */
public final class SetDescription extends UpdateActionImpl<TaxCategory> {
    @Nullable
    private final String description;

    private SetDescription(@Nullable final String description) {
        super("setDescription");
        this.description = description;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public static SetDescription of(@Nullable final String description) {
        return new SetDescription(description);
    }
}
