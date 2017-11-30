package io.sphere.sdk.products.commands.updateactions;

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
public final class SetAssetDescription extends StagedProductUpdateActionImpl<Product> {

    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;
    private final String assetId;
    private final String assetKey;
    @Nullable
    private final LocalizedString description;

    private SetAssetDescription(final String assetId,final String assetKey, @Nullable final Integer variantId, @Nullable final String sku, @Nullable final LocalizedString description, @Nullable final Boolean staged) {
        super("setAssetDescription", staged);
        this.assetId = assetId;
        this.variantId = variantId;
        this.sku = sku;
        this.description = description;
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
    public LocalizedString getDescription() {
        return description;
    }

    public static SetAssetDescription ofVariantId(final Integer variantId, final String assetId, @Nullable final LocalizedString description) {
        return ofVariantId(variantId, assetId, description, null);
    }

    public static SetAssetDescription ofVariantId(final Integer variantId, final String assetId, @Nullable final LocalizedString description, @Nullable final Boolean staged) {
        return new SetAssetDescription(assetId,null, variantId, null, description, staged);
    }

    public static SetAssetDescription ofSku(final String sku, final String assetId, @Nullable final LocalizedString description) {
        return ofSku(sku, assetId, description, null);
    }

    public static SetAssetDescription ofSku(final String sku, final String assetId, @Nullable final LocalizedString description, @Nullable final Boolean staged) {
        return new SetAssetDescription(assetId,null, null, sku, description, staged);
    }

    public static SetAssetDescription ofVariantIdAndAssetKey(final Integer variantId, final String assetId, @Nullable final LocalizedString description) {
        return ofVariantIdAndAssetKey(variantId, assetId, description, null);
    }

    public static SetAssetDescription ofVariantIdAndAssetKey(final Integer variantId, final String assetKey, @Nullable final LocalizedString description, @Nullable final Boolean staged) {
        return new SetAssetDescription(null,assetKey , variantId, null, description, staged);
    }

    public static SetAssetDescription ofSkuAndAssetKey(final String sku, final String assetId, @Nullable final LocalizedString description) {
        return ofSkuAndAssetKey(sku, assetId, description, null);
    }

    public static SetAssetDescription ofSkuAndAssetKey(final String sku, final String assetKey, @Nullable final LocalizedString description, @Nullable final Boolean staged) {
        return new SetAssetDescription(null,assetKey , null, sku, description, staged);
    }
}
