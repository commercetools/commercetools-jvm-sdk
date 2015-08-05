package io.sphere.sdk.products.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;
import java.util.function.Function;

public interface ProductByIdGet extends MetaModelGetDsl<Product, Product, ProductByIdGet, ProductExpansionModel<Product>> {
    static ProductByIdGet of(final Identifiable<Product> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static ProductByIdGet of(final String id) {
        return new ProductByIdGetImpl(id);
    }

    @Override
    ProductByIdGet plusExpansionPaths(final Function<ProductExpansionModel<Product>, ExpansionPath<Product>> m);

    @Override
    ProductByIdGet withExpansionPaths(final Function<ProductExpansionModel<Product>, ExpansionPath<Product>> m);

    @Override
    List<ExpansionPath<Product>> expansionPaths();

    @Override
    ProductByIdGet plusExpansionPaths(final ExpansionPath<Product> expansionPath);

    @Override
    ProductByIdGet withExpansionPaths(final ExpansionPath<Product> expansionPath);

    @Override
    ProductByIdGet withExpansionPaths(final List<ExpansionPath<Product>> expansionPaths);
}

