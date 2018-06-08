package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.attributes.AttributeImportDraft;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.Price;

import javax.annotation.Nullable;
import java.util.List;

final class ProductVariantImportDraftImpl extends Base implements ProductVariantImportDraft {
    @Nullable
    private final Integer id;
    @Nullable
    private final String sku;
    @Nullable
    private final String productId;
    @Nullable
    private final List<PriceDraft> prices;
    @Nullable
    private final List<AttributeImportDraft> attributes;
    @Nullable
    private final List<Image> images;

    ProductVariantImportDraftImpl(@Nullable final Integer id, @Nullable final String sku, @Nullable final List<PriceDraft> prices, @Nullable final List<Image> images, @Nullable final List<AttributeImportDraft> attributes, @Nullable final String productId) {
        this.attributes = attributes;
        this.id = id;
        this.sku = sku;
        this.prices = prices;
        this.images = images;
        this.productId = productId;
    }

    @Nullable
    @Override
    public List<AttributeImportDraft> getAttributes() {
        return attributes;
    }

    @Nullable
    @Override
    public Integer getId() {
        return id;
    }

    @Nullable
    @Override
    public List<Image> getImages() {
        return images;
    }

    @Nullable
    @Override
    public List<PriceDraft> getPrices() {
        return prices;
    }

    @Nullable
    @Override
    public String getSku() {
        return sku;
    }

    /**
     * Hack to initialize {@link LineItemImportDraft} correctly with an SKU or product ID/variant ID pair.
     * @return
     */
    @JsonIgnore
    @Override
    @Nullable
    public String getProductId() {
        return productId;
    }
}
