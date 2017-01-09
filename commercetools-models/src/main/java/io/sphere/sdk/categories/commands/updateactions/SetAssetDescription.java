package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;

/**
 * Changes the name of an asset.
 */
public final class SetAssetDescription extends UpdateActionImpl<Category> {
    private final String assetId;
    @Nullable
    private final LocalizedString description;

    private SetAssetDescription(final String assetId, @Nullable final LocalizedString description) {
        super("setAssetDescription");
        this.assetId = assetId;
        this.description = description;
    }

    public String getAssetId() {
        return assetId;
    }

    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    public static SetAssetDescription of(final String assetId, @Nullable final LocalizedString description) {
        return new SetAssetDescription(assetId, description);
    }
}
