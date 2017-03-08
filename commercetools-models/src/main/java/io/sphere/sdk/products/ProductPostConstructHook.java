package io.sphere.sdk.products;

import io.sphere.sdk.models.PostConstructHook;

import java.util.Optional;

/**
 * Provides additional initialization code for the generated {@link ProductImpl} class.
 */
interface ProductPostConstructHook extends PostConstructHook<Product> {
    /**
     * This hook
     * @param product
     */
    default void postConstruct(Product product) {
        Optional.of(product.getMasterData())
                .filter(d -> d instanceof ProductCatalogDataImpl)
                .ifPresent(d -> ((ProductCatalogDataImpl)d).setProductId(product.getId()));
    }
}
