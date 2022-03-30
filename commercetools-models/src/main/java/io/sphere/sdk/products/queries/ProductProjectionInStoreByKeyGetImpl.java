package io.sphere.sdk.products.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;


import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

final class ProductProjectionInStoreByKeyGetImpl extends MetaModelGetDslImpl<ProductProjection, ProductProjection, ProductProjectionInStoreByKeyGet, ProductProjectionExpansionModel<ProductProjection>> implements ProductProjectionInStoreByKeyGet {

    ProductProjectionInStoreByKeyGetImpl(final String storeKey, final String key) {
        super("key=" + key, JsonEndpoint.of(ProductProjection.typeReference(), "/in-store/key=" + urlEncode(storeKey) + "/product-projection"), ProductProjectionExpansionModel.of(), ProductProjectionInStoreByKeyGetImpl::new);
    }

    public ProductProjectionInStoreByKeyGetImpl(final MetaModelGetDslBuilder<ProductProjection, ProductProjection, ProductProjectionInStoreByKeyGet, ProductProjectionExpansionModel<ProductProjection>> builder) {
        super(builder);
    }

}
