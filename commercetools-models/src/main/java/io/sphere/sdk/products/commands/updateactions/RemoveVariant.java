package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductVariant;

import javax.annotation.Nullable;

/**
 * Removes a variant from a product.
 *
 * {@doc.gen intro}
 *
 * <p>By variant ID (every variant has a variantId):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#removeVariantById()}
 *
 * <p>By SKU (attention, SKU is optional field in a variant):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#removeVariantBySku()}
 *
 * @see io.sphere.sdk.products.commands.updateactions.AddVariant
 */
public final class RemoveVariant extends StagedProductUpdateActionImpl<Product> {
    @Nullable
    private final Integer id;
    @Nullable
    private final String sku;

    private RemoveVariant(@Nullable final Integer id, @Nullable final String sku, @Nullable final Boolean staged) {
        super("removeVariant", staged);
        this.id = id;
        this.sku = sku;
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    @Nullable
    public String getSku() {
        return sku;
    }

    public static RemoveVariant of(final ProductVariant variant) {
        return ofVariantId(variant.getId());
    }

    public static RemoveVariant of(final Integer id) {
        return ofVariantId(id);
    }

    public static RemoveVariant ofVariantId(final Integer id) {
        return ofVariantId(id, null);
    }

    public static RemoveVariant ofVariantId(final Integer id, @Nullable final Boolean staged) {
        return new RemoveVariant(id, null, staged);
    }

    public static RemoveVariant ofSku(final String sku) {
        return ofSku(sku, null);
    }

    public static RemoveVariant ofSku(final String sku, @Nullable final Boolean staged) {
        return new RemoveVariant(null, sku, staged);
    }
}
