package io.sphere.sdk.products;

public interface BySkuVariantIdentifier extends VariantIdentifier {
    String getSku();

    static BySkuVariantIdentifier of(final String sku) {
        return new VariantIdentifierImpl(null, null, sku);
    }
}
