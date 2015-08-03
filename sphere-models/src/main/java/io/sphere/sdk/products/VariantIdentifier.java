package io.sphere.sdk.products;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Referenceable;

/**
 * Identifies a {@link io.sphere.sdk.products.ProductVariant}.
 */
public class VariantIdentifier extends Base {
    private final String productId;
    private final Integer variantId;

    private VariantIdentifier(final String productId, final Integer variantId) {
        this.productId = productId;
        this.variantId = variantId;
    }

    public String getProductId() {
        return productId;
    }

    public Integer getVariantId() {
        return variantId;
    }

    public static VariantIdentifier of(final Referenceable<Product> product, final Integer variantId) {
        return of(product.toReference().getId(), variantId);
    }

    public static VariantIdentifier of(final String productId, final Integer variantId) {
        return new VariantIdentifier(productId, variantId);
    }
}
