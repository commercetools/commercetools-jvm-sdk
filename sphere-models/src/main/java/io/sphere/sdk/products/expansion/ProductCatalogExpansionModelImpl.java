package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;

final class ProductCatalogExpansionModelImpl<T> extends ExpansionModelImpl<T> implements ProductCatalogExpansionModel<T> {
    ProductCatalogExpansionModelImpl(final String parentPath, final String path) {
        super(parentPath, path);
    }

    @Override
    public ProductDataExpansionModel<T> current() {
        return productDataExpansion("current");
    }

    @Override
    public ProductDataExpansionModel<T> staged() {
        return productDataExpansion("staged");
    }

    private ProductDataExpansionModel<T> productDataExpansion(final String segment) {
        return new ProductDataExpansionModelImpl<>(pathExpression(), segment);
    }
}
