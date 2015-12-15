package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.products.Product;

public class ProductExpansionModel<T> extends ExpansionModel<T> {
    ProductExpansionModel() {
    }

    public ExpansionPathContainer<T> productType() {
        return expansionPath("productType");
    }

    public ExpansionPathContainer<T> taxCategory() {
        return expansionPath("taxCategory");
    }

    public ProductCatalogExpansionModel<T> masterData() {
        return new ProductCatalogExpansionModel<>(null, "masterData");
    }

    public static ProductExpansionModel<Product> of() {
        return new ProductExpansionModel<>();
    }
}
