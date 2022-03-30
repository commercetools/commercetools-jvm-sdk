package io.sphere.sdk.products.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class ProductProjectionInStoreByIdGetImpl extends MetaModelGetDslImpl<ProductProjection, ProductProjection, ProductProjectionInStoreByIdGet, ProductProjectionExpansionModel<ProductProjection>> implements ProductProjectionInStoreByIdGet {

    ProductProjectionInStoreByIdGetImpl(final String storeKey, final String id) {
        super(id, JsonEndpoint.of(ProductProjection.typeReference(), "/in-store/key=" + urlEncode(storeKey) + "/product-projection"), ProductProjectionExpansionModel.of(), ProductProjectionInStoreByIdGetImpl::new);
    }

    public ProductProjectionInStoreByIdGetImpl(final MetaModelGetDslBuilder<ProductProjection, ProductProjection, ProductProjectionInStoreByIdGet, ProductProjectionExpansionModel<ProductProjection>> builder) {
        super(builder);
    }
}
