package io.sphere.sdk.products;

import io.sphere.sdk.models.AssetDraft;
import io.sphere.sdk.products.attributes.AttributeDraft;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.List;

class ProductVariantDraftImpl extends Base implements ProductVariantDraft {
    @Nullable
    private final String sku;
    private final List<PriceDraft> prices;
    private final List<AttributeDraft> attributes;
    private final List<Image> images;
    private final String key;
    private final List<AssetDraft> assets;

    public ProductVariantDraftImpl(@Nullable final String sku, final List<PriceDraft> prices, final List<AttributeDraft> attributes, final List<Image> images, @Nullable final String key, final List<AssetDraft> assets) {
        this.sku = sku;
        this.prices = prices;
        this.attributes = attributes;
        this.images = images;
        this.key = key;
        this.assets = assets;
    }

    @Override
    @Nullable
    public String getSku() {
        return sku;
    }

    @Override
    public List<PriceDraft> getPrices() {
        return prices;
    }

    @Override
    public List<AttributeDraft> getAttributes() {
        return attributes;
    }

    @Override
    public List<Image> getImages() {
        return images;
    }

    @Nullable
    @Override
    public String getKey() {
        return key;
    }

    @Nullable
    @Override
    public List<AssetDraft> getAssets() {
        return assets;
    }
}
