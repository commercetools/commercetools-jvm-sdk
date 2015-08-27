package io.sphere.sdk.products.queries;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.products.ProductIdentifiable;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;
import java.util.function.Function;

public interface ProductProjectionByIdGet extends MetaModelGetDsl<ProductProjection, ProductProjection, ProductProjectionByIdGet, ProductProjectionExpansionModel<ProductProjection>> {

    static ProductProjectionByIdGet of(final ProductIdentifiable product, final ProductProjectionType projectionType) {
        return of(product.getId(), projectionType);
    }

    static ProductProjectionByIdGet of(final String id, final ProductProjectionType projectionType) {
        return new ProductProjectionByIdGetImpl(id, projectionType);
    }

    @Override
    ProductProjectionByIdGet plusExpansionPaths(final Function<ProductProjectionExpansionModel<ProductProjection>, ExpansionPath<ProductProjection>> m);

    @Override
    ProductProjectionByIdGet withExpansionPaths(final Function<ProductProjectionExpansionModel<ProductProjection>, ExpansionPath<ProductProjection>> m);

    @Override
    List<ExpansionPath<ProductProjection>> expansionPaths();

    @Override
    ProductProjectionByIdGet plusExpansionPaths(final ExpansionPath<ProductProjection> expansionPath);

    @Override
    ProductProjectionByIdGet withExpansionPaths(final ExpansionPath<ProductProjection> expansionPath);

    @Override
    ProductProjectionByIdGet withExpansionPaths(final List<ExpansionPath<ProductProjection>> expansionPaths);
}
