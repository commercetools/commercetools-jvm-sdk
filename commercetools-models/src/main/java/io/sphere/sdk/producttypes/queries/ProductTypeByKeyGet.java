package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;

/**
 * Retrieves a product type by a known key.
 *
 * {@include.example io.sphere.sdk.producttypes.queries.ProductTypeByKeyGetIntegrationTest#execution()}
 */
public interface ProductTypeByKeyGet extends MetaModelGetDsl<ProductType, ProductType, ProductTypeByKeyGet, ProductTypeExpansionModel<ProductType>> {
    static ProductTypeByKeyGet of(final String key) {
        return new ProductTypeByKeyGetImpl(key);
    }

    @Override
    List<ExpansionPath<ProductType>> expansionPaths();

    @Override
    ProductTypeByKeyGet plusExpansionPaths(final ExpansionPath<ProductType> expansionPath);

    @Override
    ProductTypeByKeyGet withExpansionPaths(final ExpansionPath<ProductType> expansionPath);

    @Override
    ProductTypeByKeyGet withExpansionPaths(final List<ExpansionPath<ProductType>> expansionPaths);
}
