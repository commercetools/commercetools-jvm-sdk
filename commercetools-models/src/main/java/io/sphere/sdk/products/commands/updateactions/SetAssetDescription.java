package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Changes the description of an asset.
 *
 * {@doc.gen intro}
 *
 * <p>By variant ID (every variant has a variantId):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setAssetDescriptionByVariantId()}
 *
 * <p>By SKU (attention, SKU is optional field in a variant):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setAssetDescriptionBySku()}
 */
public final class SetAssetDescription extends UpdateActionImpl<Product> {
    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;
    private final String assetId;
    @Nullable
    private final LocalizedString description;

    private SetAssetDescription(final String assetId, @Nullable final Integer variantId, @Nullable final String sku, @Nullable final LocalizedString description) {
        super("setAssetDescription");
        this.assetId = assetId;
        this.variantId = variantId;
        this.sku = sku;
        this.description = description;
    }

    public String getAssetId() {
        return assetId;
    }

    @Nullable
    public Integer getVariantId() {
        return variantId;
    }

    @Nullable
    public String getSku() {
        return sku;
    }

    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    public static SetAssetDescription ofVariantId(final Integer variantId, final String assetId, @Nullable final LocalizedString description) {
        return new SetAssetDescription(assetId, variantId, null, description);
    }

    public static SetAssetDescription ofSku(final String sku, final String assetId, @Nullable final LocalizedString description) {
        return new SetAssetDescription(assetId, null, sku, description);
    }
}
