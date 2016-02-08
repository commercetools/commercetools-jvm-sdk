package io.sphere.sdk.cartdiscounts.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.queries.ResourceQueryModelImpl;
import io.sphere.sdk.queries.LocalizedStringQuerySortingModel;
import io.sphere.sdk.queries.QueryModel;

final class CartDiscountQueryModelImpl extends ResourceQueryModelImpl<CartDiscount> implements CartDiscountQueryModel {

    CartDiscountQueryModelImpl(QueryModel<CartDiscount> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public LocalizedStringQuerySortingModel<CartDiscount> name() {
        return localizedStringQuerySortingModel("name");
    }
}
