package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.queries.ResourceQueryModelImpl;
import io.sphere.sdk.queries.LocalizedStringQuerySortingModel;
import io.sphere.sdk.queries.QueryModel;

public class CartDiscountQueryModel extends ResourceQueryModelImpl<CartDiscount> {

    private CartDiscountQueryModel(QueryModel<CartDiscount> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    public static CartDiscountQueryModel of() {
        return new CartDiscountQueryModel(null, null);
    }

    public LocalizedStringQuerySortingModel<CartDiscount> name() {
        return localizedStringQuerySortingModel("name");
    }
}
