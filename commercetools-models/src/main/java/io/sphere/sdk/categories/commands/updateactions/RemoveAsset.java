package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateActionImpl;

/**
 * Removes an asset.
 */
public final class RemoveAsset extends UpdateActionImpl<Category> {
    private final String assetId;

    public RemoveAsset(final String assetId) {
        super("removeAsset");
        this.assetId = assetId;
    }

    public String getAssetId() {
        return assetId;
    }
}
