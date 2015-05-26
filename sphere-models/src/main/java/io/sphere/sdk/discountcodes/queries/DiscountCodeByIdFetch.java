package io.sphere.sdk.discountcodes.queries;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.ByIdFetchImpl;

public class DiscountCodeByIdFetch extends ByIdFetchImpl<DiscountCode> {
    private DiscountCodeByIdFetch(final String id) {
        super(id, DiscountCodeEndpoint.ENDPOINT);
    }

    public static DiscountCodeByIdFetch of(final Identifiable<DiscountCode> discountCode) {
        return of(discountCode.getId());
    }

    public static DiscountCodeByIdFetch of(final String id) {
        return new DiscountCodeByIdFetch(id);
    }
}
