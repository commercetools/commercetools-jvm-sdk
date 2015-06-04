package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.LocalizedStringsQuerySortingModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;

import java.util.Optional;

public class CartDiscountQueryModel<T> extends DefaultModelQueryModelImpl<T> {

    private CartDiscountQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public static CartDiscountQueryModel<CartDiscount> of() {
        return new CartDiscountQueryModel<>(Optional.<QueryModelImpl<CartDiscount>>empty(), Optional.<String>empty());
    }

    public LocalizedStringsQuerySortingModel<T> name() {
        return LocalizedStringsQuerySortingModel.of(this, "name");
    }
}
