package io.sphere.sdk.products.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.products.search.PriceSelectionRequestDsl;
import io.sphere.sdk.queries.HeadDsl;
import io.sphere.sdk.queries.MetaModelGetDsl;
import io.sphere.sdk.queries.MetaModelHeadDsl;

import java.util.List;

public interface ProductByKeyHead extends MetaModelHeadDsl<Product, Product, ProductByKeyHead, ProductExpansionModel<Product>>, PriceSelectionRequestDsl<ProductByKeyHead> {

    static ProductByKeyHead of(final String key) {
        return new ProductByKeyHeadImpl(key);
    }

    @Override
    List<ExpansionPath<Product>> expansionPaths();

    @Override
    ProductByKeyHead plusExpansionPaths(final ExpansionPath<Product> expansionPath);

    @Override
    ProductByKeyHead withExpansionPaths(final ExpansionPath<Product> expansionPath);

    @Override
    ProductByKeyHead withExpansionPaths(final List<ExpansionPath<Product>> expansionPaths);
}
