package io.sphere.sdk.discountcodes.queries;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.expansion.DiscountCodeExpansionModel;
import io.sphere.sdk.queries.UltraQueryDslBuilder;
import io.sphere.sdk.queries.UltraQueryDslImpl;

final class DiscountCodeQueryImpl extends UltraQueryDslImpl<DiscountCode, DiscountCodeQuery, DiscountCodeQueryModel<DiscountCode>, DiscountCodeExpansionModel<DiscountCode>> implements DiscountCodeQuery {
    DiscountCodeQueryImpl(){
        super(DiscountCodeEndpoint.ENDPOINT.endpoint(), DiscountCodeQuery.resultTypeReference(), DiscountCodeQueryModel.of(), DiscountCodeExpansionModel.of(), DiscountCodeQueryImpl::new);
    }

    private DiscountCodeQueryImpl(final UltraQueryDslBuilder<DiscountCode, DiscountCodeQuery, DiscountCodeQueryModel<DiscountCode>, DiscountCodeExpansionModel<DiscountCode>> builder) {
        super(builder);
    }
}