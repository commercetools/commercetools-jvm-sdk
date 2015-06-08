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
    ProductByIdFetch plusExpansionPath(final Function<ProductExpansionModel<Product>, ExpansionPath<Product>> m);

    @Override
    ProductByIdFetch withExpansionPath(final Function<ProductExpansionModel<Product>, ExpansionPath<Product>> m);

    @Override
    List<ExpansionPath<Product>> expansionPaths();

    @Override
    ProductByIdFetch plusExpansionPath(final ExpansionPath<Product> expansionPath);

    @Override
    ProductByIdFetch withExpansionPath(final ExpansionPath<Product> expansionPath);

    @Override
    ProductByIdFetch withExpansionPath(final List<ExpansionPath<Product>> expansionPaths);
}

