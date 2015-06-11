package io.sphere.sdk.discountcodes.queries;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.expansion.DiscountCodeExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;

final class DiscountCodeQueryImpl extends MetaModelQueryDslImpl<DiscountCode, DiscountCodeQuery, DiscountCodeQueryModel, DiscountCodeExpansionModel<DiscountCode>> implements DiscountCodeQuery {
    DiscountCodeQueryImpl(){
        super(DiscountCodeEndpoint.ENDPOINT.endpoint(), DiscountCodeQuery.resultTypeReference(), DiscountCodeQueryModel.of(), DiscountCodeExpansionModel.of(), DiscountCodeQueryImpl::new);
    }

    private DiscountCodeQueryImpl(final MetaModelQueryDslBuilder<DiscountCode, DiscountCodeQuery, DiscountCodeQueryModel, DiscountCodeExpansionModel<DiscountCode>> builder) {
        super(builder);
    }
}