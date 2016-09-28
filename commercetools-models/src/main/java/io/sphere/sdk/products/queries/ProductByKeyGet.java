package io.sphere.sdk.products.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.expansion.ProductExpansionModel;
import io.sphere.sdk.products.search.PriceSelectionRequestDsl;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;

/**
 * Retrieves a product projection by a known key.
 *
 * {@include.example io.sphere.sdk.products.queries.ProductByKeyGetIntegrationTest#execution()}
 */
public interface ProductByKeyGet extends MetaModelGetDsl<Product, Product, ProductByKeyGet, ProductExpansionModel<Product>>, PriceSelectionRequestDsl<ProductByKeyGet> {

    static ProductByKeyGet of(final String key) {
        return new ProductByKeyGetImpl(key);
    }

    @Override
    List<ExpansionPath<Product>> expansionPaths();

    @Override
    ProductByKeyGet plusExpansionPaths(final ExpansionPath<Product> expansionPath);

    @Override
    ProductByKeyGet withExpansionPaths(final ExpansionPath<Product> expansionPath);

    @Override
    ProductByKeyGet withExpansionPaths(final List<ExpansionPath<Product>> expansionPaths);
}
