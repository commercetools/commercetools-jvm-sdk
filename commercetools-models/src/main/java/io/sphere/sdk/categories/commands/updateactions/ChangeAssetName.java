package io.sphere.sdk.categories.commands.updateactions;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;

/**
 * Changes the name of an asset.
 */
public final class ChangeAssetName extends UpdateActionImpl<Category> {
    private final String assetId;
    private final LocalizedString name;

    private ChangeAssetName(final String assetId, final LocalizedString name) {
        super("changeAssetName");
        this.assetId = assetId;
        this.name = name;
    }

    public String getAssetId() {
        return assetId;
    }

    public LocalizedString getName() {
        return name;
    }

    public static ChangeAssetName of(final String assetId, final LocalizedString name) {
        return new ChangeAssetName(assetId, name);
    }
}
