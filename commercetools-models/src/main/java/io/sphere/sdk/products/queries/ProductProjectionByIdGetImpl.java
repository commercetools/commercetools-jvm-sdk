package io.sphere.sdk.products.queries;

import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import java.util.Collections;

final class ProductProjectionByIdGetImpl extends MetaModelGetDslImpl<ProductProjection, ProductProjection, ProductProjectionByIdGet, ProductProjectionExpansionModel<ProductProjection>> implements ProductProjectionByIdGet {
    ProductProjectionByIdGetImpl(final String id, final ProductProjectionType projectionType) {
        super(ProductProjectionEndpoint.ENDPOINT, id, ProductProjectionExpansionModel.of(), ProductProjectionByIdGetImpl::new, Collections.singletonList(NameValuePair.of("staged", projectionType.isStaged().toString())));
    }

    public ProductProjectionByIdGetImpl(MetaModelGetDslBuilder<ProductProjection, ProductProjection, ProductProjectionByIdGet, ProductProjectionExpansionModel<ProductProjection>> builder) {
        super(builder);
    }
}
