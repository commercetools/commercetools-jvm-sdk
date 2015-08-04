package io.sphere.sdk.discountcodes.queries;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.expansion.DiscountCodeExpansionModel;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

final class DiscountCodeByIdGetImpl extends MetaModelGetDslImpl<DiscountCode, DiscountCode, DiscountCodeByIdGet, DiscountCodeExpansionModel<DiscountCode>> implements DiscountCodeByIdGet {
    DiscountCodeByIdGetImpl(final String id) {
        super(id, DiscountCodeEndpoint.ENDPOINT, DiscountCodeExpansionModel.of(), DiscountCodeByIdGetImpl::new);
    }

    public DiscountCodeByIdGetImpl(MetaModelGetDslBuilder<DiscountCode, DiscountCode, DiscountCodeByIdGet, DiscountCodeExpansionModel<DiscountCode>> builder) {
        super(builder);
    }
}
