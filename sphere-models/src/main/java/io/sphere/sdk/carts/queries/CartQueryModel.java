package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.queries.QueryModel;

public class CartQueryModel extends CartLikeQueryModel<Cart> {

    public static CartQueryModel of() {
        return new CartQueryModel(null, null);
    }

    private CartQueryModel(QueryModel<Cart> parent, String pathSegment) {
        super(parent, pathSegment);
    }

}
