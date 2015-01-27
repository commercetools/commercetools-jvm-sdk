package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.queries.FetchByIdImpl;

/**
 Gets a cart by ID.

 {@include.example io.sphere.sdk.carts.queries.CartFetchByIdTest#fetchById()}
 */
public class CartFetchById extends FetchByIdImpl<Cart> {
    private CartFetchById(final String id) {
        super(id, CartsEndpoint.ENDPOINT);
    }

    public static CartFetchById of(final String id) {
        return new CartFetchById(id);
    }
}
