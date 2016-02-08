package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;
import java.util.function.Function;

/**
 * Retrieves a product type by a known ID.
 *
 * {@include.example io.sphere.sdk.producttypes.queries.ProductTypeByIdGetTest#execution()}
 */
public interface ProductTypeByIdGet extends MetaModelGetDsl<ProductType, ProductType, ProductTypeByIdGet, ProductTypeExpansionModel<ProductType>> {
    static ProductTypeByIdGet of(final Identifiable<ProductType> productType) {
        return of(productType.getId());
    }

    static ProductTypeByIdGet of(final String id) {
        return new ProductTypeByIdGetImpl(id);
    }

    @Override
    List<ExpansionPath<ProductType>> expansionPaths();

    @Override
    ProductTypeByIdGet plusExpansionPaths(final ExpansionPath<ProductType> expansionPath);

    @Override
    ProductTypeByIdGet withExpansionPaths(final ExpansionPath<ProductType> expansionPath);

    @Override
    ProductTypeByIdGet withExpansionPaths(final List<ExpansionPath<ProductType>> expansionPaths);
}
