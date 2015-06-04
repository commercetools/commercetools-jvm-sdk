package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.queries.DefaultModelQueryModelImpl;
import io.sphere.sdk.queries.QueryModel;

import java.util.Optional;

public class CartQueryModel<T> extends DefaultModelQueryModelImpl<T> {

    public static CartQueryModel<Cart> of() {
        return new CartQueryModel<>(Optional.empty(), Optional.<String>empty());
    }

    private CartQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

}
