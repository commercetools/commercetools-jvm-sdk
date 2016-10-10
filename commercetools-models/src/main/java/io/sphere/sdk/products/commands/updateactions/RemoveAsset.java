package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Removes an asset.
 *
 * {@doc.gen intro}
 *
 * <p>By variant ID (every variant has a variantId):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#removeAssetByVariantId()}
 *
 * <p>By SKU (attention, SKU is optional field in a variant):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#removeAssetBySku()}
 */
public final class RemoveAsset extends UpdateActionImpl<Product> {
    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;
    private final String assetId;

    private RemoveAsset(final String assetId, @Nullable final Integer variantId, @Nullable final String sku) {
        super("removeAsset");
        this.assetId = assetId;
        this.variantId = variantId;
        this.sku = sku;
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

    public static RemoveAsset ofVariantId(final Integer variantId, final String assetId) {
        return new RemoveAsset(assetId, variantId, null);
    }

    public static RemoveAsset ofSku(final String sku, final String assetId) {
        return new RemoveAsset(assetId, null, sku);
    }
}
