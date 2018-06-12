package io.sphere.sdk.orders;

import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.attributes.AttributeImportDraft;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.Price;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public final class ProductVariantImportDraftBuilder extends Base implements Builder<ProductVariantImportDraft> {

    @Nullable
    private final Integer id;
    @Nullable
    private final String sku;
    @Nullable
    private final String productId;
    @Nullable
    private List<PriceDraft> prices;
    @Nullable
    private List<AttributeImportDraft> attributes;
    @Nullable
    private List<Image> images;

    private ProductVariantImportDraftBuilder(@Nullable final String sku, @Nullable final String productId, @Nullable final Integer id) {
        this.sku = sku;
        this.productId = productId;
        this.id = id;
    }

    /**
     * The prices of the variant. The prices should not contain two prices for the same price scope (same currency, country and customer group). If this property is defined, then it will override the prices property from the original product variant, otherwise prices property from the original product variant would be copied in the resulting order.
     * @param prices the prices to set
     * @return this builder
     */
    public ProductVariantImportDraftBuilder prices(@Nullable final List<PriceDraft> prices) {
        this.prices = prices;
        return this;
    }

    public ProductVariantImportDraftBuilder attributes(@Nullable final List<AttributeImportDraft> attributes) {
        this.attributes = attributes;
        return this;
    }

    public ProductVariantImportDraftBuilder attributes(final AttributeImportDraft ... attributes) {
        return attributes(asList(attributes));
    }

    public ProductVariantImportDraftBuilder images(@Nullable final List<Image> images) {
        this.images = images;
        return this;
    }

    public static ProductVariantImportDraftBuilder ofSku(final String sku) {
        Objects.requireNonNull(sku);
        return new ProductVariantImportDraftBuilder(sku, null, null);
    }

    public static ProductVariantImportDraftBuilder of(final String productId, final Integer variantId, final String sku) {
        Objects.requireNonNull(productId);
        Objects.requireNonNull(variantId);
        Objects.requireNonNull(sku);
        return new ProductVariantImportDraftBuilder(sku, productId, variantId);
    }

    public static ProductVariantImportDraftBuilder of(final String productId, final Integer variantId) {
        Objects.requireNonNull(productId);
        Objects.requireNonNull(variantId);
        return new ProductVariantImportDraftBuilder(null, productId, variantId);
    }

    @Override
    public ProductVariantImportDraft build() {
        return new ProductVariantImportDraftImpl(id, sku, prices, images, attributes, productId);
    }
}
