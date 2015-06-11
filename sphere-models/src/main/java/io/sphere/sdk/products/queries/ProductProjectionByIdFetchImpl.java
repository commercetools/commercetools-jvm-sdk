package io.sphere.sdk.products.queries;

import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;

import static java.util.Arrays.asList;

final class ProductProjectionByIdFetchImpl extends MetaModelFetchDslImpl<ProductProjection, ProductProjectionByIdFetch, ProductProjectionExpansionModel<ProductProjection>> implements ProductProjectionByIdFetch {
    ProductProjectionByIdFetchImpl(final String id, final ProductProjectionType projectionType) {
        super(ProductProjectionEndpoint.ENDPOINT, id, ProductProjectionExpansionModel.of(), ProductProjectionByIdFetchImpl::new, asList(HttpQueryParameter.of("staged", projectionType.isStaged().toString())));
    }

    public ProductProjectionByIdFetchImpl(MetaModelFetchDslBuilder<ProductProjection, ProductProjectionByIdFetch, ProductProjectionExpansionModel<ProductProjection>> builder) {
        super(builder);
    }
}
