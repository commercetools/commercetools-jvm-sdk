package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.AssetSource;

import java.util.List;

/**
 * Sets the sources of an asset.
 */
public final class SetAssetSources extends UpdateActionImpl<Category> {
    private final String assetId;
    private final List<AssetSource> sources;

    private SetAssetSources(final String assetId, final List<AssetSource> sources) {
        super("setAssetSources");
        this.assetId = assetId;
        this.sources = sources;
    }

    public String getAssetId() {
        return assetId;
    }

    public List<AssetSource> getSources() {
        return sources;
    }

    public static SetAssetSources of(final String assetId, final List<AssetSource> sources) {
        return new SetAssetSources(assetId, sources);
    }
}