package io.sphere.sdk.productdiscounts.queries;

import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.expansion.ProductDiscountExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;


final class ProductDiscountQueryImpl extends MetaModelQueryDslImpl<ProductDiscount, ProductDiscountQuery, ProductDiscountQueryModel, ProductDiscountExpansionModel<ProductDiscount>> implements ProductDiscountQuery {
    ProductDiscountQueryImpl(){
        super(ProductDiscountEndpoint.ENDPOINT.endpoint(), ProductDiscountQuery.resultTypeReference(), ProductDiscountQueryModel.of(), ProductDiscountExpansionModel.of(), ProductDiscountQueryImpl::new);
    }

    private ProductDiscountQueryImpl(final MetaModelQueryDslBuilder<ProductDiscount, ProductDiscountQuery, ProductDiscountQueryModel, ProductDiscountExpansionModel<ProductDiscount>> builder) {
        super(builder);
    }
}