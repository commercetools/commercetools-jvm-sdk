package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductVariant;

import javax.annotation.Nullable;

/**
 * Adds the given price to the product variant's prices set. It is rejected if the product already contains a price with the same price scope (same currency, country, customer group and channel).
 *
 * {@doc.gen intro}
 *
 * <p>By variant ID (every variant has a variantId):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#addPriceByVariantId()}
 *
 * <p>By SKU (attention, SKU is optional field in a variant):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#addPriceBySku()}
 *
 * @see ProductVariant#getPrices()
 */
public final class AddPrice extends StagedProductUpdateActionImpl<Product> {
    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;
    private final PriceDraft price;

    private AddPrice(@Nullable final Integer variantId, @Nullable final String sku, final PriceDraft price, @Nullable final Boolean staged) {
        super("addPrice", staged);
        this.variantId = variantId;
        this.sku = sku;
        this.price = price;
    }

    @Nullable
    public Integer getVariantId() {
        return variantId;
    }

    @Nullable
    public String getSku() {
        return sku;
    }

    public PriceDraft getPrice() {
        return price;
    }

    public static AddPrice of(final Integer variantId, final PriceDraft price) {
        return ofVariantId(variantId, price);
    }

    public static AddPrice ofVariantId(final Integer variantId, final PriceDraft price) {
        return ofVariantId(variantId, price, null);
    }

    public static AddPrice ofVariantId(final Integer variantId, final PriceDraft price, @Nullable final Boolean staged) {
        return new AddPrice(variantId, null, price, staged);
    }

    public static AddPrice ofSku(final String sku, final PriceDraft price) {
        return ofSku(sku, price, null);
    }

    public static AddPrice ofSku(final String sku, final PriceDraft price, @Nullable final Boolean staged) {
        return new AddPrice(null, sku, price, staged);
    }
}
