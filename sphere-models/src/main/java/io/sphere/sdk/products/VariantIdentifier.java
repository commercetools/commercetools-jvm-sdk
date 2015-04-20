package io.sphere.sdk.products;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Referenceable;

/**
 * Identifies a {@link io.sphere.sdk.products.ProductVariant}.
 */
public class VariantIdentifier extends Base {
    private final String productId;
    private final int variantId;

    private VariantIdentifier(final String productId, final int variantId) {
        this.productId = productId;
        this.variantId = variantId;
    }

    public String getProductId() {
        return productId;
    }

    public int getVariantId() {
        return variantId;
    }

    public static VariantIdentifier of(final Referenceable<Product> product, final int variantId) {
        return of(product.toReference().getId(), variantId);
    }

    public static VariantIdentifier of(final String productId, final int variantId) {
        return new VariantIdentifier(productId, variantId);
    }
}
