package io.sphere.sdk.productdiscounts.queries;

import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.expansion.ProductDiscountExpansionModel;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;

final class ProductDiscountByIdFetchImpl extends MetaModelFetchDslImpl<ProductDiscount, ProductDiscount, ProductDiscountByIdFetch, ProductDiscountExpansionModel<ProductDiscount>> implements ProductDiscountByIdFetch {
    ProductDiscountByIdFetchImpl(final String id) {
        super(id, ProductDiscountEndpoint.ENDPOINT, ProductDiscountExpansionModel.of(), ProductDiscountByIdFetchImpl::new);
    }

    public ProductDiscountByIdFetchImpl(MetaModelFetchDslBuilder<ProductDiscount, ProductDiscount, ProductDiscountByIdFetch, ProductDiscountExpansionModel<ProductDiscount>> builder) {
        super(builder);
    }
}
