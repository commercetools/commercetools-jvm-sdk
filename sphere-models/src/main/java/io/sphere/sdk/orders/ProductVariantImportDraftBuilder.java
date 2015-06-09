package io.sphere.sdk.orders;

import io.sphere.sdk.attributes.AttributeDraft;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.Image;
import io.sphere.sdk.products.Price;

import java.util.List;
import java.util.Optional;

public class ProductVariantImportDraftBuilder extends Base implements Builder<ProductVariantImportDraft> {
    private final Optional<Integer> id;
    private final Optional<String> sku;
    private final Optional<String> productId;
    private Optional<List<Price>> prices = Optional.empty();
    private Optional<List<AttributeDraft>> attributes = Optional.empty();
    private Optional<List<Image>> images = Optional.empty();

    private ProductVariantImportDraftBuilder(final Optional<String> sku, final Optional<String> productId, final Optional<Integer> id) {
        this.sku = sku;
        this.productId = productId;
        this.id = id;
    }

    public ProductVariantImportDraftBuilder prices(final Optional<List<Price>> prices) {
        this.prices = prices;
        return this;
    }

    /**
     * The prices of the variant. The prices should not contain two prices for the same price scope (same currency, country and customer group). If this property is defined, then it will override the prices property from the original product variant, otherwise prices property from the original product variant would be copied in the resulting order.
     * @param prices the prices to set
     * @return this builder
     */
    public ProductVariantImportDraftBuilder prices(final List<Price> prices) {
        return prices(Optional.of(prices));
    }

    public ProductVariantImportDraftBuilder attributes(final Optional<List<AttributeDraft>> attributes) {
        this.attributes = attributes;
        return this;
    }

    public ProductVariantImportDraftBuilder attributes(final List<AttributeDraft> attributes) {
        return attributes(Optional.of(attributes));
    }

    public ProductVariantImportDraftBuilder images(final Optional<List<Image>> images) {
        this.images = images;
        return this;
    }

    public ProductVariantImportDraftBuilder images(final List<Image> images) {
        return images(Optional.of(images));
    }

    public static ProductVariantImportDraftBuilder ofSku(final String sku) {
        return new ProductVariantImportDraftBuilder(Optional.of(sku), Optional.<String>empty(), Optional.<Integer>empty());
    }

    public static ProductVariantImportDraftBuilder of(final String productId, final int variantId, final String sku) {
        return new ProductVariantImportDraftBuilder(Optional.of(sku), Optional.of(productId), Optional.of(variantId));
    }

    public static ProductVariantImportDraftBuilder of(final String productId, final int variantId) {
        return new ProductVariantImportDraftBuilder(Optional.<String>empty(), Optional.of(productId), Optional.of(variantId));
    }

    @Override
    public ProductVariantImportDraft build() {
        return new ProductVariantImportDraftImpl(id, sku, prices, images, attributes, productId);
    }
}
