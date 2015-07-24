package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;

/**
 * Adds, changes or removes an SKU on a product variant.
 * An SKU can only be changed or removed from a variant through this operation
 * if there is no inventory entry associated with that SKU.
 * This change can never be staged and is thus immediately visible in published products.
 *
 * {@include.example io.sphere.sdk.products.queries.ProductProjectionQueryTest#queryBySku()}
 */
public class SetSku extends UpdateAction<Product> {
    private final int variantId;@Nullable

    private final String sku;

    private SetSku(final int variantId, final String sku) {
        super("setSKU");
        this.variantId = variantId;
        this.sku = sku;
    }

    public int getVariantId() {
        return variantId;
    }

    public String getSku() {
        return sku;
    }

    public static SetSku of(final int variantId, @Nullable final String sku) {
        return new SetSku(variantId, sku);
    }
}
