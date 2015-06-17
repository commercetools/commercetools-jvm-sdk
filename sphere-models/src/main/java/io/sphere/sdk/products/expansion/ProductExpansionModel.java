package io.sphere.sdk.products.expansion;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.queries.ExpansionPath;

import java.util.Optional;

public class ProductExpansionModel<T> extends ExpansionModel<T> {
    ProductExpansionModel() {
    }

    public ExpansionPath<T> productType() {
        return expansionPath("productType");
    }

    public ExpansionPath<T> taxCategory() {
        return expansionPath("taxCategory");
    }

    public ProductCatalogExpansionModel<T> masterData() {
        return new ProductCatalogExpansionModel<>(Optional.empty(), Optional.of("masterData"));
    }

    public static ProductExpansionModel<Product> of() {
        return new ProductExpansionModel<>();
    }
}
