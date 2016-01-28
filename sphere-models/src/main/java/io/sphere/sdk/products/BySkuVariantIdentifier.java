package io.sphere.sdk.products;

/**
 * Identifies a {@link ProductVariant} by its SKU.
 *
 *
 */
public interface BySkuVariantIdentifier extends VariantIdentifier {
    String getSku();

    static BySkuVariantIdentifier of(final String sku) {
        return new VariantIdentifierImpl(null, null, sku);
    }
}
