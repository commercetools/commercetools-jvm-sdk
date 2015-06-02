package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.queries.ExpansionPath;

import java.util.Optional;

public class ProductExpansionModel<T> extends ExpansionModel<T> {
    ProductExpansionModel() {
    }

    public ExpansionPath<T> productType() {
        return pathWithRoots("productType");
    }

    public ExpansionPath<T> taxCategory() {
        return pathWithRoots("taxCategory");
    }

    public ProductCatalogExpansionModel<T> masterData() {
        return new ProductCatalogExpansionModel<>(Optional.empty(), Optional.of("masterData"));
    }
}
