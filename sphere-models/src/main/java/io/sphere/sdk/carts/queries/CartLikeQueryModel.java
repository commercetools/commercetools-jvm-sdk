package io.sphere.sdk.carts.queries;

import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.MoneyQueryModel;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

import java.util.Optional;

abstract class CartLikeQueryModel<T> extends DefaultModelQueryModelImpl<T> {
    protected CartLikeQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<T> customerId() {
        return stringModel("customerId");
    }

    public StringQuerySortingModel<T> customerEmail() {
        return stringModel("customerEmail");
    }

    public MoneyQueryModel<T> totalPrice() {
        return moneyModel("totalPrice");
    }
}
