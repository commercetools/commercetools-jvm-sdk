package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.models.AssetSource;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * Sets the sources of an asset.
 */
public final class SetAssetSources extends StagedProductUpdateActionImpl<Product> {
    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;
    private final String assetId;
    private final String assetKey;
    @Nullable
    private final List<AssetSource> sources;

    private SetAssetSources(final String assetId,final String assetKey, @Nullable final Integer variantId, @Nullable final String sku, @Nullable final List<AssetSource> sources, @Nullable final Boolean staged) {
        super("setAssetSources", Optional.ofNullable(staged).orElse(true));
        this.assetId = assetId;
        this.variantId = variantId;
        this.sku = sku;
        this.sources = sources;
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
    public List<AssetSource> getSources() {
        return sources;
    }

    public static SetAssetSources ofVariantId(final Integer variantId, final String assetId, @Nullable final List<AssetSource> sources) {
        return ofVariantId(variantId, assetId, sources, null);
    }

    public static SetAssetSources ofVariantId(final Integer variantId, final String assetId, @Nullable final List<AssetSource> sources, @Nullable final Boolean staged) {
        return new SetAssetSources(assetId,null, variantId, null, sources, staged);
    }

    public static SetAssetSources ofSku(final String sku, final String assetId, @Nullable final List<AssetSource> sources) {
        return ofSku(sku, assetId, sources, null);
    }

    public static SetAssetSources ofSku(final String sku, final String assetId, @Nullable final List<AssetSource> sources, @Nullable final Boolean staged) {
        return new SetAssetSources(assetId,null, null, sku, sources, staged);
    }

    public static SetAssetSources ofVariantIdAndAssetKey(final Integer variantId, final String assetKey, @Nullable final List<AssetSource> sources) {
        return ofVariantIdAndAssetKey(variantId, assetKey, sources, null);
    }

    public static SetAssetSources ofVariantIdAndAssetKey(final Integer variantId, final String assetKey, @Nullable final List<AssetSource> sources, @Nullable final Boolean staged) {
        return new SetAssetSources(null, assetKey, variantId, null, sources, staged);
    }

    public static SetAssetSources ofSkuAndAssetKey(final String sku, final String assetKey, @Nullable final List<AssetSource> sources) {
        return ofSkuAndAssetKey(sku, assetKey, sources, null);
    }

    public static SetAssetSources ofSkuAndAssetKey(final String sku, final String assetKey, @Nullable final List<AssetSource> sources, @Nullable final Boolean staged) {
        return new SetAssetSources(null, assetKey, null, sku, sources, staged);
    }

}
