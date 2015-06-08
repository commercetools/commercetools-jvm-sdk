package io.sphere.sdk.discountcodes.queries;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.expansion.DiscountCodeExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.MetaModelFetchDsl;

public interface DiscountCodeByIdFetch extends MetaModelFetchDsl<DiscountCode, DiscountCodeByIdFetch, DiscountCodeExpansionModel<DiscountCode>> {
    static DiscountCodeByIdFetch of(final Identifiable<DiscountCode> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static DiscountCodeByIdFetch of(final String id) {
        return new DiscountCodeByIdFetchImpl(id);
    }
}

