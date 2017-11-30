package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Sets the tags of an asset.
 */
public final class SetAssetTags extends UpdateActionImpl<Category> {
    private final String assetId;
    private final String assetKey;
    @Nullable
    private final Set<String> tags;

    private SetAssetTags(final String assetId, final String assetKey,@Nullable final Set<String> tags) {
        super("setAssetTags");
        this.assetId = assetId;
        this.assetKey = assetKey;
        this.tags = tags;
    }

    public String getAssetId() {
        return assetId;
    }

    public String getAssetKey() {
        return assetKey;
    }

    @Nullable
    public Set<String> getTags() {
        return tags;
    }

    public static SetAssetTags of(final String assetId, @Nullable final Set<String> tags) {
        return new SetAssetTags(assetId, null, tags);
    }

    public static SetAssetTags ofKey(final String assetKey, @Nullable final Set<String> tags) {
        return new SetAssetTags(null, assetKey, tags);
    }


}
