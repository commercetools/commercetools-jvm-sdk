package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductVariant;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Changes the key of a {@link io.sphere.sdk.products.ProductVariant}, for {@link Product}s use {@link SetKey}.
 *
 * {@doc.gen intro}
 *
 * Use the variantId to select the variant
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setProductVariantKeyByVariantId()}
 * or use the sku to select the variant
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setProductVariantKeyBySku()}
 *
 * @see ProductVariant#getKey()
 */
public final class SetProductVariantKey extends StagedProductUpdateActionImpl<Product> {
    @Nullable
    private final String key;
    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;

    private SetProductVariantKey(@Nullable final String key, @Nullable final Integer variantId, @Nullable final String sku, @Nullable final Boolean staged) {
        super("setProductVariantKey", staged);
        this.key = key;
        this.variantId = variantId;
        this.sku = sku;
    }

    @Nullable
    public String getKey() {
        return key;
    }

    @Nullable
    public Integer getVariantId() {
        return variantId;
    }

    @Nullable
    public String getSku() {
        return sku;
    }

    public static SetProductVariantKey ofKeyAndVariantId(@Nullable final String key, @Nonnull final Integer variantId) {
        return ofKeyAndVariantId(key, variantId, null);
    }

    public static SetProductVariantKey ofKeyAndVariantId(@Nullable final String key, @Nonnull final Integer variantId, @Nullable final Boolean staged) {
        return new SetProductVariantKey(key, variantId, null, staged);
    }

    public static SetProductVariantKey ofKeyAndSku(@Nullable final String key, @Nonnull final String sku) {
        return ofKeyAndSku(key, sku, null);
    }

    public static SetProductVariantKey ofKeyAndSku(@Nullable final String key, @Nonnull final String sku, @Nullable final Boolean staged) {
        return new SetProductVariantKey(key, null, sku, staged);
    }
}
