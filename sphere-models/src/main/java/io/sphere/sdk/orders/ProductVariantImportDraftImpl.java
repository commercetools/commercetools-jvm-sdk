package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.attributes.AttributeDraft;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Image;
import io.sphere.sdk.products.Price;

import java.util.List;
import java.util.Optional;

final class ProductVariantImportDraftImpl extends Base implements ProductVariantImportDraft {
    private final Optional<Integer> id;
    private final Optional<String> sku;
    private final Optional<String> productId;
    private final Optional<List<Price>> prices;
    private final Optional<List<AttributeDraft>> attributes;
    private final Optional<List<Image>> images;

    ProductVariantImportDraftImpl(final Optional<Integer> id, final Optional<String> sku, final Optional<List<Price>> prices, final Optional<List<Image>> images, final Optional<List<AttributeDraft>> attributes, final Optional<String> productId) {
        this.attributes = attributes;
        this.id = id;
        this.sku = sku;
        this.prices = prices;
        this.images = images;
        this.productId = productId;
    }

    @Override
    public Optional<List<AttributeDraft>> getAttributes() {
        return attributes;
    }

    @Override
    public Optional<Integer> getId() {
        return id;
    }

    @Override
    public Optional<List<Image>> getImages() {
        return images;
    }

    @Override
    public Optional<List<Price>> getPrices() {
        return prices;
    }

    @Override
    public Optional<String> getSku() {
        return sku;
    }

    /**
     * Hack to initialize {@link LineItemImportDraft} correctly with an SKU or product ID/variant ID pair.
     * @return
     */
    @JsonIgnore
    @Override
    public Optional<String> getProductId() {
        return productId;
    }
}
