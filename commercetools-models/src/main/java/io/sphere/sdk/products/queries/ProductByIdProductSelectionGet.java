package io.sphere.sdk.products.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.products.search.PriceSelectionRequestDsl;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;

public interface ProductByIdProductSelectionGet extends MetaModelGetDsl<Product, Product, ProductByIdProductSelectionGet, ProductExpansionModel<Product>> {

    static ProductByIdProductSelectionGet of(final Identifiable<Product> product) {
        return of(product.getId());
    }

    static ProductByIdProductSelectionGet of(final String id) {
        return new ProductByIdProductSelectionGetImpl(id);
    }

    @Override
    List<ExpansionPath<Product>> expansionPaths();

    @Override
    ProductByIdProductSelectionGet plusExpansionPaths(final ExpansionPath<Product> expansionPath);

    @Override
    ProductByIdProductSelectionGet withExpansionPaths(final ExpansionPath<Product> expansionPath);

    @Override
    ProductByIdProductSelectionGet withExpansionPaths(final List<ExpansionPath<Product>> expansionPaths);
}

