package io.sphere.sdk.products.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.products.search.PriceSelectionRequestDsl;
import io.sphere.sdk.queries.HeadDsl;
import io.sphere.sdk.queries.MetaModelHeadDsl;

import java.util.List;

public interface ProductByIdHead extends MetaModelHeadDsl<Product, Product, ProductByIdHead, ProductExpansionModel<Product>>, PriceSelectionRequestDsl<ProductByIdHead> {
    static ProductByIdHead of(final Identifiable<Product> product) {
        return of(product.getId());
    }

    static ProductByIdHead of(final String id) {
        return new ProductByIdHeadImpl(id);
    }

    @Override
    List<ExpansionPath<Product>> expansionPaths();

    @Override
    ProductByIdHead plusExpansionPaths(final ExpansionPath<Product> expansionPath);

    @Override
    ProductByIdHead withExpansionPaths(final ExpansionPath<Product> expansionPath);

    @Override
    ProductByIdHead withExpansionPaths(final List<ExpansionPath<Product>> expansionPaths);
}

