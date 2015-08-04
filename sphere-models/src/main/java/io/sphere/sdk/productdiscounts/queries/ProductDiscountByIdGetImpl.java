package io.sphere.sdk.productdiscounts.queries;

import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.expansion.ProductDiscountExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

final class ProductDiscountByIdGetImpl extends MetaModelGetDslImpl<ProductDiscount, ProductDiscount, ProductDiscountByIdGet, ProductDiscountExpansionModel<ProductDiscount>> implements ProductDiscountByIdGet {
    ProductDiscountByIdGetImpl(final String id) {
        super(id, ProductDiscountEndpoint.ENDPOINT, ProductDiscountExpansionModel.of(), ProductDiscountByIdGetImpl::new);
    }

    public ProductDiscountByIdGetImpl(MetaModelGetDslBuilder<ProductDiscount, ProductDiscount, ProductDiscountByIdGet, ProductDiscountExpansionModel<ProductDiscount>> builder) {
        super(builder);
    }
}
