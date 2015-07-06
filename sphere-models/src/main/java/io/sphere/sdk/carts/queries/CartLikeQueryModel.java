package io.sphere.sdk.carts.queries;

import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.StringQuerySortingModel;

import java.util.Optional;

public class CartLikeQueryModel<T> extends DefaultModelQueryModelImpl<T> {

    public static CartLikeQueryModel of() {
        return new CartLikeQueryModel(Optional.empty(), Optional.<String>empty());
    }

    protected CartLikeQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<T> customerId() {
        return stringModel("customerId");
    }

    public StringQuerySortingModel<T> customerEmail() {
        return stringModel("customerEmail");
    }
}
