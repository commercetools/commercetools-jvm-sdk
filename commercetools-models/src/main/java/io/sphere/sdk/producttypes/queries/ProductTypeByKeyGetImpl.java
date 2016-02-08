package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

final class ProductTypeByKeyGetImpl extends MetaModelGetDslImpl<ProductType, ProductType, ProductTypeByKeyGet, ProductTypeExpansionModel<ProductType>> implements ProductTypeByKeyGet {
    ProductTypeByKeyGetImpl(final String key) {
        super("key=" + key, ProductTypeEndpoint.ENDPOINT, ProductTypeExpansionModel.of(), ProductTypeByKeyGetImpl::new);
    }

    public ProductTypeByKeyGetImpl(final MetaModelGetDslBuilder<ProductType, ProductType, ProductTypeByKeyGet, ProductTypeExpansionModel<ProductType>> builder) {
        super(builder);
    }
}
