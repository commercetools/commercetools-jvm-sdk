package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.LocalizedStringsQuerySortingModel;
import io.sphere.sdk.queries.QueryModel;

public class CartDiscountQueryModel extends DefaultModelQueryModelImpl<CartDiscount> {

    private CartDiscountQueryModel(QueryModel<CartDiscount> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    public static CartDiscountQueryModel of() {
        return new CartDiscountQueryModel(null, null);
    }

    public LocalizedStringsQuerySortingModel<CartDiscount> name() {
        return LocalizedStringsQuerySortingModel.of(this, "name");
    }
}
