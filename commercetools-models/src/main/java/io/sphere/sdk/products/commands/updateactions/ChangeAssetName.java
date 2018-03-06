package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Changes the name of an asset.
 *
 * {@doc.gen intro}
 *
 * <p>By variant ID (every variant has a variantId):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#changeAssetNameByVariantId()}
 *
 * <p>By SKU (attention, SKU is optional field in a variant):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#changeAssetNameBySku()}
 */
public final class ChangeAssetName extends StagedProductUpdateActionImpl<Product> {
    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;
    private final String assetId;
    private final String assetKey;
    private final LocalizedString name;

    private ChangeAssetName(@Nullable final String assetId,@Nullable final String assetKey, @Nullable final Integer variantId, @Nullable final String sku, final LocalizedString name, @Nullable final Boolean staged) {
        super("changeAssetName", staged);
        this.assetId = assetId;
        this.assetKey = assetKey;
        this.variantId = variantId;
        this.sku = sku;
        this.name = name;
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

    public LocalizedString getName() {
        return name;
    }

    public static ChangeAssetName ofVariantId(final Integer variantId, final String assetId, final LocalizedString name) {
        return ofVariantId(variantId, assetId, name, null);
    }

    public static ChangeAssetName ofVariantId(final Integer variantId, final String assetId, final LocalizedString name, @Nullable final Boolean staged) {
        return new ChangeAssetName(assetId,null, variantId, null, name, staged);
    }

    public static ChangeAssetName ofSku(final String sku, final String assetId, final LocalizedString name) {
        return ofSku(sku, assetId, name, null);
    }

    public static ChangeAssetName ofSku(final String sku, final String assetId, final LocalizedString name, @Nullable final Boolean staged) {
        return new ChangeAssetName(assetId,null,  null, sku, name, staged);
    }

    public static ChangeAssetName ofAssetKeyAndVariantId(final Integer variantId, final String assetKey, final LocalizedString name) {
        return ofAssetKeyAndVariantId(variantId, assetKey, name, null);
    }

    public static ChangeAssetName ofAssetKeyAndVariantId(final Integer variantId, final String assetKey, final LocalizedString name, @Nullable final Boolean staged) {
        return new ChangeAssetName(null,assetKey, variantId, null, name, staged);
    }

    public static ChangeAssetName ofAssetKeyAndSku(final String sku, final String assetKey, final LocalizedString name) {
        return ofAssetKeyAndSku(sku, assetKey, name, null);
    }

    public static ChangeAssetName ofAssetKeyAndSku(final String sku, final String assetKey, final LocalizedString name, @Nullable final Boolean staged) {
        return new ChangeAssetName(null
                ,assetKey,  null, sku, name, staged);
    }

}
