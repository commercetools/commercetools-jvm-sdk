package io.sphere.sdk.products.expansion;

import io.sphere.sdk.queries.ExpansionModel;

import java.util.Optional;

public class ProductCatalogExpansionModel<T> extends ExpansionModel<T> {
    ProductCatalogExpansionModel(final Optional<String> parentPath, final Optional<String> path) {
        super(parentPath, path);
    }

    public ProductDataExpansionModel<T> current() {
        return productDataExpansion("current");
    }

    public ProductDataExpansionModel<T> staged() {
        return productDataExpansion("staged");
    }

    private ProductDataExpansionModel<T> productDataExpansion(final String segment) {
        return new ProductDataExpansionModel<>(pathExpressionOption(), segment);
    }
}
