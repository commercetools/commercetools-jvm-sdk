package io.sphere.sdk.products.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.queries.ExpansionPath;
import io.sphere.sdk.queries.MetaModelFetchDsl;

import java.util.List;
import java.util.function.Function;

public interface ProductByIdFetch extends MetaModelFetchDsl<Product, ProductByIdFetch, ProductExpansionModel<Product>> {
    static ProductByIdFetch of(final Identifiable<Product> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static ProductByIdFetch of(final String id) {
        return new ProductByIdFetchImpl(id);
    }

    @Override
    ProductByIdFetch plusExpansionPaths(final Function<ProductExpansionModel<Product>, ExpansionPath<Product>> m);

    @Override
    ProductByIdFetch withExpansionPaths(final Function<ProductExpansionModel<Product>, ExpansionPath<Product>> m);

    @Override
    List<ExpansionPath<Product>> expansionPaths();

    @Override
    ProductByIdFetch plusExpansionPaths(final ExpansionPath<Product> expansionPath);

    @Override
    ProductByIdFetch withExpansionPaths(final ExpansionPath<Product> expansionPath);

    @Override
    ProductByIdFetch withExpansionPaths(final List<ExpansionPath<Product>> expansionPaths);
}

