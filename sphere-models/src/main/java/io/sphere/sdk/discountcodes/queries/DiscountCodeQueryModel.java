package io.sphere.sdk.discountcodes.queries;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.queries.ResourceQueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

public interface DiscountCodeQueryModel extends ResourceQueryModel<DiscountCode> {
    StringQuerySortingModel<DiscountCode> code();

    static DiscountCodeQueryModel of() {
        return new DiscountCodeQueryModelImpl(null, null);
    }
}
