package io.sphere.sdk.products.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;

public interface ProductByKeyProductSelectionGet extends MetaModelGetDsl<Product, Product, ProductByKeyProductSelectionGet, ProductExpansionModel<Product>> {

    static ProductByKeyProductSelectionGet of(final String key) {
        return new ProductByKeyProductSelectionGetImpl(key);
    }

    @Override
    List<ExpansionPath<Product>> expansionPaths();

    @Override
    ProductByKeyProductSelectionGet plusExpansionPaths(final ExpansionPath<Product> expansionPath);

    @Override
    ProductByKeyProductSelectionGet withExpansionPaths(final ExpansionPath<Product> expansionPath);

    @Override
    ProductByKeyProductSelectionGet withExpansionPaths(final List<ExpansionPath<Product>> expansionPaths);
}

