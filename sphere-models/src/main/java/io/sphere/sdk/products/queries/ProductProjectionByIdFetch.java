package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.queries.MetaModelFetchDsl;

public interface ProductProjectionByIdFetch extends MetaModelFetchDsl<ProductProjection, ProductProjectionByIdFetch, ProductProjectionExpansionModel<ProductProjection>> {

    static ProductProjectionByIdFetch of(final String id, final ProductProjectionType projectionType) {
        return new ProductProjectionByIdFetchImpl(id, projectionType);
    }
}
