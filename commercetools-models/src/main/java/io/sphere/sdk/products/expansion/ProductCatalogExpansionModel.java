package io.sphere.sdk.products.expansion;

public interface ProductCatalogExpansionModel<T> {
    ProductDataExpansionModel<T> current();

    ProductDataExpansionModel<T> staged();
}
