package io.sphere.sdk.products;

import io.sphere.sdk.models.Referenceable;


/**
 * Identifies a {@link io.sphere.sdk.products.ProductVariant} by product ID and variant ID.
 *
 * @see ProductVariant#getIdentifier()
 */
public interface ByIdVariantIdentifier extends VariantIdentifier {
    String getProductId();

    Integer getVariantId();

    static ByIdVariantIdentifier of(final Referenceable<Product> product, final Integer variantId) {
        return of(product.toReference().getId(), variantId);
    }

    static ByIdVariantIdentifier of(final String productId, final Integer variantId) {
        return new VariantIdentifierImpl(productId, variantId, null);
    }
}
