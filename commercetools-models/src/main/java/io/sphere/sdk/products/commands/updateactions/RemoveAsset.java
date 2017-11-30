package io.sphere.sdk.products.commands.updateactions;

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
public final class RemoveAsset extends StagedProductUpdateActionImpl<Product> {
    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;
    private final String assetId;
    private final String assetKey;

    private RemoveAsset(final String assetId,final String assetKey, @Nullable final Integer variantId, @Nullable final String sku, @Nullable final Boolean staged) {
        super("removeAsset", staged);
        this.assetId = assetId;
        this.assetKey = assetKey;
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

    public String getAssetKey() {
        return assetKey;
    }
    public static RemoveAsset ofVariantId(final Integer variantId, final String assetId) {
        return ofVariantId(variantId, assetId, null);
    }

    public static RemoveAsset ofVariantId(final Integer variantId, final String assetId, @Nullable final Boolean staged) {
        return new RemoveAsset(assetId,null, variantId, null, staged);
    }

    public static RemoveAsset ofSku(final String sku, final String assetId) {
        return ofSku(sku, assetId, null);
    }

    public static RemoveAsset ofSku(final String sku, final String assetId, @Nullable final Boolean staged) {
        return new RemoveAsset(assetId,null, null, sku, staged);
    }

    public static RemoveAsset ofVariantIdWithKey(final Integer variantId, final String assetKey) {
        return ofVariantIdWithKey(variantId, assetKey, null);
    }

    public static RemoveAsset ofVariantIdWithKey(final Integer variantId, final String assetKey, @Nullable final Boolean staged) {
        return new RemoveAsset(null,assetKey, variantId, null, staged);
    }

    public static RemoveAsset ofSkuWithKey(final String sku, final String assetKey) {
        return ofSku(null, assetKey, null);
    }

    public static RemoveAsset ofSkuWithKey(final String sku, final String assetKey, @Nullable final Boolean staged) {
        return new RemoveAsset(null,assetKey, null, sku, staged);
    }


}
