package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.AssetDraft;

/**
 * Adds an asset.
 */
public final class AddAsset extends UpdateActionImpl<Category> {
    private final AssetDraft asset;

    private AddAsset(final AssetDraft asset) {
        super("addAsset");
        this.asset = asset;
    }

    public AssetDraft getAsset() {
        return asset;
    }

    public static AddAsset of(final AssetDraft asset) {
        return new AddAsset(asset);
    }
}