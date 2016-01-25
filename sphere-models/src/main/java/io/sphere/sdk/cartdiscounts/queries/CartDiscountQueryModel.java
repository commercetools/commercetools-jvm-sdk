package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.queries.LocalizedStringQuerySortingModel;
import io.sphere.sdk.queries.ResourceQueryModel;

public interface CartDiscountQueryModel extends ResourceQueryModel<CartDiscount> {
    LocalizedStringQuerySortingModel<CartDiscount> name();

    static CartDiscountQueryModel of() {
        return new CartDiscountQueryModelImpl(null, null);
    }
}
