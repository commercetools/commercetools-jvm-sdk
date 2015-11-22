package io.sphere.sdk.producttypes.queries;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

final class ProductTypeByIdGetImpl extends MetaModelGetDslImpl<ProductType, ProductType, ProductTypeByIdGet, ProductTypeExpansionModel<ProductType>> implements ProductTypeByIdGet {
    ProductTypeByIdGetImpl(final String id) {
        super(id, ProductTypeEndpoint.ENDPOINT, ProductTypeExpansionModel.of(), ProductTypeByIdGetImpl::new);
    }

    public ProductTypeByIdGetImpl(final MetaModelGetDslBuilder<ProductType, ProductType, ProductTypeByIdGet, ProductTypeExpansionModel<ProductType>> builder) {
        super(builder);
    }
}
