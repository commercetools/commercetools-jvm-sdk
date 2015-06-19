package io.sphere.sdk.products.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelFetchDsl;

import java.util.List;
import java.util.function.Function;

public interface ProductProjectionByIdFetch extends MetaModelFetchDsl<ProductProjection, ProductProjectionByIdFetch, ProductProjectionExpansionModel<ProductProjection>> {

    static ProductProjectionByIdFetch of(final Identifiable<ProductProjection> product, final ProductProjectionType projectionType) {
        return of(product.getId(), projectionType);
    }

    static ProductProjectionByIdFetch of(final String id, final ProductProjectionType projectionType) {
        return new ProductProjectionByIdFetchImpl(id, projectionType);
    }

    @Override
    ProductProjectionByIdFetch plusExpansionPaths(final Function<ProductProjectionExpansionModel<ProductProjection>, ExpansionPath<ProductProjection>> m);

    @Override
    ProductProjectionByIdFetch withExpansionPaths(final Function<ProductProjectionExpansionModel<ProductProjection>, ExpansionPath<ProductProjection>> m);

    @Override
    List<ExpansionPath<ProductProjection>> expansionPaths();

    @Override
    ProductProjectionByIdFetch plusExpansionPaths(final ExpansionPath<ProductProjection> expansionPath);

    @Override
    ProductProjectionByIdFetch withExpansionPaths(final ExpansionPath<ProductProjection> expansionPath);

    @Override
    ProductProjectionByIdFetch withExpansionPaths(final List<ExpansionPath<ProductProjection>> expansionPaths);
}
