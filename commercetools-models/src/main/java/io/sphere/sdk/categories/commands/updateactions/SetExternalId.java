package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;

/**
 * Changes the external id of a category.
 *
 * {@include.example io.sphere.sdk.categories.commands.CategoryUpdateCommandIntegrationTest#setExternalId()}
 */
public final class SetExternalId extends UpdateActionImpl<Category> {
    @Nullable
    private final String externalId;

    private SetExternalId(@Nullable final String externalId) {
        super("setExternalId");
        this.externalId = externalId;
    }

    public static SetExternalId of(final String externalId) {
        return new SetExternalId(externalId);
    }

    @Nullable
    public String getExternalId() {
        return externalId;
    }
}
