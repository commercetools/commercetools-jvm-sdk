package io.sphere.sdk.products.queries;


import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDsl;


public interface ProductProjectionInStoreByKeyGet extends MetaModelGetDsl<ProductProjection, ProductProjection, ProductProjectionInStoreByKeyGet, ProductProjectionExpansionModel<ProductProjection>> {

    static ProductProjectionInStoreByKeyGet of(final String storeKey, final String id) {
        return new ProductProjectionInStoreByKeyGetImpl(storeKey, id);
    }
    
}
