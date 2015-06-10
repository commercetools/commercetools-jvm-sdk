package io.sphere.sdk.discountcodes.queries;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.expansion.DiscountCodeExpansionModel;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;

final class DiscountCodeByIdFetchImpl extends MetaModelFetchDslImpl<DiscountCode, DiscountCodeByIdFetch, DiscountCodeExpansionModel<DiscountCode>> implements DiscountCodeByIdFetch {
    DiscountCodeByIdFetchImpl(final String id) {
        super(id, DiscountCodeEndpoint.ENDPOINT, DiscountCodeExpansionModel.of(), DiscountCodeByIdFetchImpl::new);
    }

    public DiscountCodeByIdFetchImpl(MetaModelFetchDslBuilder<DiscountCode, DiscountCodeByIdFetch, DiscountCodeExpansionModel<DiscountCode>> builder) {
        super(builder);
    }
}
