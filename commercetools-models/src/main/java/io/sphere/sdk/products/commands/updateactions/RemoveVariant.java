package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductVariant;

import javax.annotation.Nullable;

/**
 * Removes a variant from a product.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#addVariant()}
 *
 * * <p>By variant ID (every variant has a variantId):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#removeVariantById()}
 *
 * <p>By SKU (attention, SKU is optional field in a variant):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#removeVariantBySku()}
 *
 * @see io.sphere.sdk.products.commands.updateactions.AddVariant
 */
public final class RemoveVariant extends UpdateActionImpl<Product> {
    @Nullable
    private final Integer id;
    @Nullable
    private final String sku;

    private RemoveVariant(@Nullable final Integer id, @Nullable final String sku) {
        super("removeVariant");
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
        return new RemoveVariant(id, null);
    }

    public static RemoveVariant ofVariantSku(final String sku) {
        return new RemoveVariant(null, sku);
    }
}
