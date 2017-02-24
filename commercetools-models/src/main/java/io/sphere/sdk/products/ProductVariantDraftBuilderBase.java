package io.sphere.sdk.products;

import io.sphere.sdk.models.AssetDraft;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.products.attributes.AttributeDraft;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

abstract class ProductVariantDraftBuilderBase<T extends ProductVariantDraftBuilderBase> extends Base implements Builder<ProductVariantDraft> {
    @Nullable
    protected String sku;

    protected List<PriceDraft> prices = Collections.emptyList();

    protected List<AttributeDraft> attributes = Collections.emptyList();

    protected List<Image> images = Collections.emptyList();

    protected List<AssetDraft> assets = Collections.emptyList();

    @Nullable
    protected String key;

    protected final T self;

    protected ProductVariantDraftBuilderBase() {
        self = (T) this;
    }

    public T sku(@Nullable final String sku) {
        this.sku = sku;
        return self;
    }

    public T images(final List<Image> images) {
        this.images = images != null ? images : Collections.emptyList();
        return self;
    }
    public T prices(final List<PriceDraft> prices) {
        this.prices = prices != null ? prices : Collections.emptyList();
        return self;
    }

    public T attributes(final List<AttributeDraft> attributes) {
        this.attributes = attributes != null ? attributes : Collections.emptyList();
        return self;
    }

    public T key(@Nullable final String key) {
        this.key = key;
        return self;
    }

    public T assets(@Nullable final List<AssetDraft> assets) {
        this.assets = assets;
        return self;
    }

    protected T from(final ProductVariantDraft template) {
        return (T) sku(template.getSku())
                .prices(template.getPrices())
                .attributes(template.getAttributes())
                .images(template.getImages())
                .key(template.getKey())
                .assets(template.getAssets());
    }

    @Override
    public ProductVariantDraft build() {
        return new ProductVariantDraftImpl(sku, prices, attributes, images, key, assets);
    }
}
