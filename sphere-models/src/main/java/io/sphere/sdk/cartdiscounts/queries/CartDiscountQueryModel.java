package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.LocalizedStringsQuerySortingModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;

import java.util.Optional;

public class CartDiscountQueryModel extends DefaultModelQueryModelImpl<CartDiscount> {

    private CartDiscountQueryModel(Optional<? extends QueryModel<CartDiscount>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public static CartDiscountQueryModel of() {
        return new CartDiscountQueryModel(Optional.<QueryModelImpl<CartDiscount>>empty(), Optional.<String>empty());
    }

    public LocalizedStringsQuerySortingModel<CartDiscount> name() {
        return LocalizedStringsQuerySortingModel.of(this, "name");
    }
}
