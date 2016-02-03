package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionModel;

public final class ProductCatalogExpansionModel<T> extends ExpansionModel<T> {
    ProductCatalogExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    public ProductDataExpansionModel<T> current() {
        return productDataExpansion("current");
    }

    public ProductDataExpansionModel<T> staged() {
        return productDataExpansion("staged");
    }

    private ProductDataExpansionModel<T> productDataExpansion(final String segment) {
        return new ProductDataExpansionModel<>(pathExpression(), segment);
    }
}
