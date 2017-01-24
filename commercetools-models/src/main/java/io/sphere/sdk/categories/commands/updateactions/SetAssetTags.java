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
    @Nullable
    private final Set<String> tags;

    private SetAssetTags(final String assetId, @Nullable final Set<String> tags) {
        super("setAssetTags");
        this.assetId = assetId;
        this.tags = tags;
    }

    public String getAssetId() {
        return assetId;
    }

    @Nullable
    public Set<String> getTags() {
        return tags;
    }

    public static SetAssetTags of(final String assetId, @Nullable final Set<String> tags) {
        return new SetAssetTags(assetId, tags);
    }
}
