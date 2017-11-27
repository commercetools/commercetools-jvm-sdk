package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.AssetDraft;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Adds an asset.
 *
 * {@doc.gen intro}
 *
 * <p>By variant ID (every variant has a variantId):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#addAssetByVariantId()}
 *
 * <p>By SKU (attention, SKU is optional field in a variant):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#addAssetBySku()}
 */
public final class AddAsset extends StagedProductUpdateActionImpl<Product> {
    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;
    private final AssetDraft asset;

    @Nullable
    private final Integer position;

    private AddAsset(final AssetDraft asset, @Nullable final Integer variantId, @Nullable final String sku,@Nullable Boolean staged,@Nullable Integer position) {
        super("addAsset",staged);
        this.asset = asset;
        this.variantId = variantId;
        this.sku = sku;
        this.position = position;
    }

    public AssetDraft getAsset() {
        return asset;
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
    public Boolean getStaged() {
        return staged;
    }

    @Nullable
    public Integer getPosition() {
        return position;
    }

    public static AddAsset ofVariantId(final Integer variantId, final AssetDraft asset) {
        return new AddAsset(asset, variantId, null, null, null);
    }

    public static AddAsset ofSku(final String sku, final AssetDraft asset) {
        return new AddAsset(asset, null, sku, null, null);
    }

    public AddAsset withPosition(@Nullable final Integer position){
        return new AddAsset(getAsset(),getVariantId(),getSku(),getStaged(),position);
    }

    public AddAsset withStaged(@Nullable final Boolean staged){
        return new AddAsset(getAsset(),getVariantId(),getSku(),staged ,getPosition());
    }
}
