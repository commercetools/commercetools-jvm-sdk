package io.sphere.sdk.products.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.products.search.PriceSelectionRequestDsl;
import io.sphere.sdk.queries.MetaModelHeadDsl;

import java.util.List;

public interface ProductsHead extends MetaModelHeadDsl<Product, Product, ProductsHead, ProductExpansionModel<Product>>, PriceSelectionRequestDsl<ProductsHead> {

    static ProductsHead of() {
        return new ProductsHeadImpl();
    }

    @Override
    List<ExpansionPath<Product>> expansionPaths();

    @Override
    ProductsHead plusExpansionPaths(final ExpansionPath<Product> expansionPath);

    @Override
    ProductsHead withExpansionPaths(final ExpansionPath<Product> expansionPath);

    @Override
    ProductsHead withExpansionPaths(final List<ExpansionPath<Product>> expansionPaths);
}
