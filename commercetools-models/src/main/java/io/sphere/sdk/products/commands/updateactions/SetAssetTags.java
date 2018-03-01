package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * Sets the tags of an asset.
 *
 * {@doc.gen intro}
 *
 * <p>By variant ID (every variant has a variantId):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setAssetTagsByVariantId()}
 *
 * <p>By SKU (attention, SKU is optional field in a variant):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setAssetTagsBySku()}
 */
public final class SetAssetTags extends StagedProductUpdateActionImpl<Product> {
    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;
    private final String assetId;
    private final String assetKey;
    @Nullable
    private final Set<String> tags;

    private SetAssetTags(final String assetId,final String assetKey, @Nullable final Integer variantId, @Nullable final String sku, @Nullable final Set<String> tags, @Nullable final Boolean staged) {
        super("setAssetTags", staged);
        this.assetId = assetId;
        this.variantId = variantId;
        this.sku = sku;
        this.tags = tags;
        this.assetKey = assetKey;
    }

    public String getAssetId() {
        return assetId;
    }

    public String getAssetKey() {
        return assetKey;
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
    public Set<String> getTags() {
        return tags;
    }

    public static SetAssetTags ofVariantId(final Integer variantId, final String assetId, @Nullable final Set<String> tags) {
        return ofVariantId(variantId, assetId, tags, null);
    }

    public static SetAssetTags ofVariantId(final Integer variantId, final String assetId, @Nullable final Set<String> tags, @Nullable final Boolean staged) {
        return new SetAssetTags(assetId,null, variantId, null, tags, staged);
    }

    public static SetAssetTags ofSku(final String sku, final String assetId, @Nullable final Set<String> tags) {
        return ofSku(sku, assetId, tags, null);
    }

    public static SetAssetTags ofSku(final String sku, final String assetId, @Nullable final Set<String> tags, @Nullable final Boolean staged) {
        return new SetAssetTags(assetId,null, null, sku, tags, staged);
    }

    public static SetAssetTags ofVariantIdAndAssetKey(final Integer variantId, final String assetKey, @Nullable final Set<String> tags) {
        return ofVariantIdAndAssetKey(variantId, assetKey, tags, null);
    }

    public static SetAssetTags ofVariantIdAndAssetKey(final Integer variantId, final String assetKey, @Nullable final Set<String> tags, @Nullable final Boolean staged) {
        return new SetAssetTags(null, assetKey, variantId, null, tags, staged);
    }

    public static SetAssetTags ofSkuAndAssetKey(final String sku, final String assetKey, @Nullable final Set<String> tags) {
        return ofSkuAndAssetKey(sku, assetKey, tags, null);
    }

    public static SetAssetTags ofSkuAndAssetKey(final String sku, final String assetKey, @Nullable final Set<String> tags, @Nullable final Boolean staged) {
        return new SetAssetTags(null,assetKey, null, sku, tags, staged);
    }
}
