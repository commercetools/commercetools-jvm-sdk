package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Changes the order of the assets list. The new order is defined by listing the ids of the assets.
 *
 * {@doc.gen intro}
 *
 * <p>By variant ID (every variant has a variantId):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#changeAssetOrderByVariantId()}
 *
 * <p>By SKU (attention, SKU is optional field in a variant):</p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#changeAssetOrderBySku()}
 */
public final class ChangeAssetOrder extends StagedProductUpdateActionImpl<Product> {
    @Nullable
    private final Integer variantId;
    @Nullable
    private final String sku;
    private final List<String> assetOrder;

    private ChangeAssetOrder(final List<String> assetOrder, @Nullable final Integer variantId, @Nullable final String sku, @Nullable final Boolean staged) {
        super("changeAssetOrder", staged);
        this.assetOrder = assetOrder;
        this.variantId = variantId;
        this.sku = sku;
    }

    public List<String> getAssetOrder() {
        return assetOrder;
    }

    @Nullable
    public Integer getVariantId() {
        return variantId;
    }

    @Nullable
    public String getSku() {
        return sku;
    }

    public static ChangeAssetOrder ofVariantId(final Integer variantId, final List<String> assetOrder) {
        return ofVariantId(variantId, assetOrder, null);
    }

    public static ChangeAssetOrder ofVariantId(final Integer variantId, final List<String> assetOrder, @Nullable final Boolean staged) {
        return new ChangeAssetOrder(assetOrder, variantId, null, staged);
    }

    public static ChangeAssetOrder ofSku(final String sku, final List<String> assetOrder) {
        return ofSku(sku, assetOrder, null);
    }

    public static ChangeAssetOrder ofSku(final String sku, final List<String> assetOrder, @Nullable final Boolean staged) {
        return new ChangeAssetOrder(assetOrder, null, sku, staged);
    }
}
