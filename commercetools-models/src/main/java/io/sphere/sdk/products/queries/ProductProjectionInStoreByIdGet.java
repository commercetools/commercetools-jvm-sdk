package io.sphere.sdk.products.queries;


import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDsl;

public interface ProductProjectionInStoreByIdGet extends MetaModelGetDsl<ProductProjection, ProductProjection, ProductProjectionInStoreByIdGet, ProductProjectionExpansionModel<ProductProjection>> {
    
    static ProductProjectionInStoreByIdGet of(final String storeKey, final String id) {
        return new ProductProjectionInStoreByIdGetImpl(storeKey, id);
    }
    
}
