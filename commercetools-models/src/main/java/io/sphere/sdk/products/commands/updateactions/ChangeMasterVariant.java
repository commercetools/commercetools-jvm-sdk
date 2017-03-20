package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;

import javax.annotation.Nullable;

/**
 * Sets the given variant as the new master variant. The old master variant is added to the back of the list of variants.
 *
 * {@doc.gen intro}
 *
 *
 * <p>By variant ID (every variant has a variantId):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#changeMasterVariantWithVariantId()}
 *
 * <p>By SKU (attention, SKU is optional field in a variant):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#changeMasterVariantWithSku()}
 *
 * @see ProductProjection#getMasterVariant()
 * @see ProductProjection#getVariants()
 */
public final class ChangeMasterVariant extends StagedProductUpdateActionImpl<Product> {
    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;

    private ChangeMasterVariant(@Nullable final Integer variantId, @Nullable final String sku, @Nullable final Boolean staged) {
        super("changeMasterVariant", staged);
        this.variantId = variantId;
        this.sku = sku;
    }

    public static ChangeMasterVariant ofSku(final String sku) {
        return ofSku(sku, null);
    }

    public static ChangeMasterVariant ofSku(final String sku, @Nullable final Boolean staged) {
        return new ChangeMasterVariant(null, sku, staged);
    }

    public static ChangeMasterVariant ofVariantId(final Integer variantId) {
        return ofVariantId(variantId, null);
    }

    public static ChangeMasterVariant ofVariantId(final Integer variantId, @Nullable final Boolean staged) {
        return new ChangeMasterVariant(variantId, null, staged);
    }

    @Nullable
    public Integer getVariantId() {
        return variantId;
    }

    @Nullable
    public String getSku() {
        return sku;
    }
}
