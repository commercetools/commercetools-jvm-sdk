package io.sphere.sdk.products.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.products.search.PriceSelectionRequestDsl;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;

public interface ProductByIdGet extends MetaModelGetDsl<Product, Product, ProductByIdGet, ProductExpansionModel<Product>>, PriceSelectionRequestDsl<ProductByIdGet> {
    static ProductByIdGet of(final Identifiable<Product> product) {
        return of(product.getId());
    }

    static ProductByIdGet of(final String id) {
        return new ProductByIdGetImpl(id);
    }

    @Override
    List<ExpansionPath<Product>> expansionPaths();

    @Override
    ProductByIdGet plusExpansionPaths(final ExpansionPath<Product> expansionPath);

    @Override
    ProductByIdGet withExpansionPaths(final ExpansionPath<Product> expansionPath);

    @Override
    ProductByIdGet withExpansionPaths(final List<ExpansionPath<Product>> expansionPaths);
}

