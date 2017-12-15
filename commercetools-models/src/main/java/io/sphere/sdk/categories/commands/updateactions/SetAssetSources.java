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
    private final String assetKey;
    private final List<AssetSource> sources;

    private SetAssetSources(final String assetId, final String assetKey, final List<AssetSource> sources) {
        super("setAssetSources");
        this.assetId = assetId;
        this.assetKey = assetKey;
        this.sources = sources;
    }

    public String getAssetId() {
        return assetId;
    }

    public List<AssetSource> getSources() {
        return sources;
    }

    public String getAssetKey() {
        return assetKey;
    }

    public static SetAssetSources of(final String assetId, final List<AssetSource> sources) {
        return new SetAssetSources(assetId,null, sources);
    }

    public static SetAssetSources ofKey(final String assetKey, final List<AssetSource> sources) {
        return new SetAssetSources(null ,assetKey, sources);
    }


}