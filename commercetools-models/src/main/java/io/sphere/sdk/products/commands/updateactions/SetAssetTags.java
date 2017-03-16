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
    @Nullable
    private final Set<String> tags;

    private SetAssetTags(final String assetId, @Nullable final Integer variantId, @Nullable final String sku, @Nullable final Set<String> tags, @Nullable final Boolean staged) {
        super("setAssetTags", staged);
        this.assetId = assetId;
        this.variantId = variantId;
        this.sku = sku;
        this.tags = tags;
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
    public Set<String> getTags() {
        return tags;
    }

    public static SetAssetTags ofVariantId(final Integer variantId, final String assetId, @Nullable final Set<String> tags) {
        return ofVariantId(variantId, assetId, tags, null);
    }

    public static SetAssetTags ofVariantId(final Integer variantId, final String assetId, @Nullable final Set<String> tags, @Nullable final Boolean staged) {
        return new SetAssetTags(assetId, variantId, null, tags, staged);
    }

    public static SetAssetTags ofSku(final String sku, final String assetId, @Nullable final Set<String> tags) {
        return ofSku(sku, assetId, tags, null);
    }

    public static SetAssetTags ofSku(final String sku, final String assetId, @Nullable final Set<String> tags, @Nullable final Boolean staged) {
        return new SetAssetTags(assetId, null, sku, tags, staged);
    }
}
